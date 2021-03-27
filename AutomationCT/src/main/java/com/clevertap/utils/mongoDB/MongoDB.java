package com.clevertap.utils.mongoDB;

import com.mongodb.*;
import com.mongodb.util.JSON;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * To get instance of any collection from underlying db.
 * Application should use this collection to construct Dao.
 * Note: Need to call  connect() or connect(ip,port) before using getDBCollection();
 */
public class MongoDB {
    public static final class ErrorCodes {
        public static final int DUPLICATE_KEY_ON_INSERT = 11000;
    }

    private static String ip = "localhost"; // will be default..
    private static int port = 27017;
    public static String DB_ADMIN = "admin";
    private static boolean isReadOnlyUser = false;

    public static boolean isReadOnlyUser() {
        return isReadOnlyUser;
    }

    /* 0 is root: rest are subordinates */
    private static MongoClient[] clients = new MongoClient[11];
    private static MongoClientOptions clientOptions;
    private static List<MongoCredential> mongoCredentialList;
    private static List<ServerAddress> seedServers;

    public static MongoClientOptions getClientOptions() {
        return clientOptions;
    }

    public static List<MongoCredential> getMongoCredentialList() {
        return mongoCredentialList;
    }

    /**
     * intializes all mongo clusters.
     *
     * @param seeds
     * @param mongoCredentialList
     * @param clientOptions
     */
    public synchronized static void connect(List<ServerAddress> seeds, List<MongoCredential> mongoCredentialList, MongoClientOptions clientOptions) {
        if (clients[0] == null) {
            MongoDB.mongoCredentialList = mongoCredentialList;
            MongoDB.clientOptions = clientOptions;
            MongoDB.seedServers = seeds;
            clients[0] = connectCluster(seeds, "root");
            // connect with subordinates

            List<BasicDBObject> rows = new ArrayList<BasicDBObject>();
            rows.add((BasicDBObject)JSON.parse("{ \"_id\" : 1, \"name\" : \"hotstar\", \"mongo\" : [ { \"host\" : \"127.0.0.1\", \"port\" : 27017 } ] }"));
            for (BasicDBObject row : rows) {
                _connect(row);
            }
        }
    }

    private static void processCredentials(List<MongoCredential> mongoCredentialList) {
        if (mongoCredentialList != null && mongoCredentialList.size() > 0) {
            String userName = mongoCredentialList.get(0).getUserName();
            isReadOnlyUser = "ro".equals(userName);
        }
    }

    private static MongoClient connectCluster(List<ServerAddress> cluster, String name) {
        MongoClient _mongo = null;
//        logger.info("Connecting to cluster name: "+name);
        if (cluster != null && cluster.size() == 1) {  // to support dev environments having only one Mongo instance
//            logger.info("******* SINGLE CONNECTION *********");
            ServerAddress serverAddress = cluster.get(0);
            if (mongoCredentialList == null || mongoCredentialList.size() == 0) {
                _mongo = new MongoClient(serverAddress, clientOptions);
            } else {
                _mongo = new MongoClient(serverAddress, mongoCredentialList, clientOptions);
                processCredentials(mongoCredentialList);
            }
        } else {                                  // to support production environments
//            logger.info("******* REPL CONNECTION *********");
            if (cluster != null && cluster.size() > 1) {
                if (mongoCredentialList == null) {
                    _mongo = new MongoClient(cluster, clientOptions);
                } else {
                    _mongo = new MongoClient(cluster, mongoCredentialList, clientOptions);
                    processCredentials(mongoCredentialList);
                }
                List<ServerAddress> serverAddressList = _mongo.getServerAddressList();
                for (ServerAddress aServerAddress : serverAddressList) {
//                    logger.info("Mongo connected/discovered: " + aServerAddress.getHost() + ":" + aServerAddress.getPort());
                }
            } else {
//                logger.error("No mongo servers specified for connection");
                throw new MongoException("Illegal Ip or Port specified for connection");
            }
        }
        return _mongo;
    }

    public static List<ServerAddress> getServers(BasicDBObject row) {
        BasicDBList list = (BasicDBList) row.get("mongo");
        List<ServerAddress> servers = new ArrayList<ServerAddress>();
        for (Object mongos : list) {
            BasicDBObject aMongos = (BasicDBObject) mongos;
            servers.add(new ServerAddress(aMongos.getString("host"), aMongos.getInt("port", 27017)));
        }
        return servers;
    }

    private static void _connect(BasicDBObject row) {
        int ordinal = row.getInt("_id");
        String name = row.getString("name");
        List<ServerAddress> servers = getServers(row);
        clients[ordinal] = connectCluster(servers, name);
    }
    /**
     * call this only if the account context is set in threadlocal
     * @return
     */

    /**
     * call this only if the account context is set in threadlocal
     *
     * @return
     */

    public static int getClusterOrdinal() {
//        AccountMeta meta = AccountMeta.CURRENT.get();
//        if ( meta == null ){
//            String accountId = "1200000000";
//            return getClusterOrdinal(accountId);
//        }
//        return meta.mongoClusterOrdinal;
        return 1;
    }

    public static int getClusterOrdinal(String accountId) {
//        String accountIdOld = "1200000000";
//        try{
//            AppConstants.ACCOUNT_ID.set(accountIdOld);
//            return AccountMetaDao.instance.getAccountMeta(true).mongoClusterOrdinal;
//        }finally{
//            AppConstants.ACCOUNT_ID.set(accountIdOld);
//        }
        return 1;
    }

    public static DB getDB(String dbName, int clusterOrdinal) {
        MongoClient client = clients[clusterOrdinal];
        if (client == null) {
            synchronized (clients) {
                client = clients[clusterOrdinal];
                if (client == null) {
                    if (seedServers == null) {
                        String msg = "Query received but not connected to the database. Call MongoDB.connect() explicitly.";
//                        logger.error(msg);
                        throw new MongoException(msg);
                    }
                    if (seedServers == null) {
                        String msg = "Query received but not connected to the database. Call MongoDB.connect() explicitly.";
//                        logger.error(msg);
                        throw new MongoException(msg);
                    }
                    if (clusterOrdinal == 0) {
                        connectCluster(seedServers, "root");
                    } else {
                        //BasicDBObject row = MongoClusterDao.instance.findOne(new BasicDBObject("_id", clusterOrdinal));
                        BasicDBObject row = (BasicDBObject) JSON.parse("{ \"_id\" : 1, \"name\" : \"hotstar\", \"mongo\" : [ { \"host\" : \"127.0.0.1\", \"port\" : 27017 } ] }");
                        _connect(row);
                    }
                }
            }
        }
        return clients[clusterOrdinal].getDB(dbName);
    }

    /**
     * Used by KeyValueMappingDao as the collection names are dynamic and not pre-defined
     *
     * @param dbName
     * @param collectionNameString
     * @return
     * @throws UnknownHostException
     * @throws MongoException
     */
    public static DBCollection getDBCollection(String dbName, String collectionNameString, int clusterOrdinal) throws UnknownHostException, MongoException {
        String dbNameString = "1200000000";
        DB db = null;
        try {
            db = getDB(dbNameString, clusterOrdinal);
            return db.getCollection(collectionNameString);
        } catch (MongoSocketException men) {
            if (clients[clusterOrdinal] != null) {
                try {
                    clients[clusterOrdinal].close();
                } catch (Throwable t) {
                }
                clients[clusterOrdinal] = null;
            }
            db = getDB(dbNameString, clusterOrdinal);
            return db.getCollection(collectionNameString);
        }
    }

    public static boolean isConnected() {
        return clients[0] != null;
    }

    protected void finalize() throws Throwable {
        super.finalize();
        for (int i = 0; i < clients.length; i++) {
            if (clients[i] != null) {
                try {
                    clients[i].close();
                } catch (Throwable t) {
                }
            }
        }
    }
}