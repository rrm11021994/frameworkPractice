package com.clevertap.utils.mongoDB;

import com.mongodb.*;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class MongoUtil {

    public static void connectionWrapper(){
    int connectionsPerHost = 50;
//    String serverStr = ApplicationProperties.getProperty("MONGO.SERVERS");
//    String mongoReadPreference = ApplicationProperties.getProperty("MONGO.READ_PREF");
//    String userName = ApplicationProperties.getProperty("MONGO.USER_NAME");
//    String password = ApplicationProperties.getProperty("MONGO.PASSWORD");

    String serverStr = "127.0.0.1:27017";
    String mongoReadPreference = "primaryPreferred";
    String userName = "";
    String password = "";
            try{
//        connectionsPerHost = Integer.parseInt(ApplicationProperties.getProperty("MONGO.CONN_PER_HOST"));
    } catch(
    Exception ex)

    { /* ignored as we've set default values */}
            try

    {
        String[] servers = serverStr.split(",");
        List<ServerAddress> seedServers = new ArrayList<ServerAddress>();
        if (servers != null) {
            for (String server1 : servers) {
//                String server = server1.trim();
//                int idx = server.indexOf(":");
//                int port = Integer.parseInt(server.substring(idx + 1));
//                String host = server.substring(0, idx).trim();
                String[] hostAndPort = server1.split(";");
                String host = hostAndPort[0].trim();
                int port = Integer.parseInt(hostAndPort[1].trim());
                seedServers.add(new ServerAddress(host, port));
            }
        }
        ReadPreference readPreference = ReadPreference.primary(); // in case of missing property
        if ("primary".equals(mongoReadPreference)) {
            readPreference = ReadPreference.primary();
        } else if ("primaryPreferred".equals(mongoReadPreference)) {
            readPreference = ReadPreference.primaryPreferred();
        } else if ("secondary".equals(mongoReadPreference)) {
            readPreference = ReadPreference.secondary();
        } else if ("secondaryPreferred".equals(mongoReadPreference)) {
            readPreference = ReadPreference.secondaryPreferred();
        }
        MongoClientOptions clientOptions = new MongoClientOptions.Builder()
                .readPreference(readPreference)
                .connectionsPerHost(connectionsPerHost)
                .build();
//        logger.info("Setting up mongo connectivity with-> readPreference:" + readPreference + ", connPerHost: " + connectionsPerHost);
        System.out.println("Setting up mongo connectivity with-> readPreference:" + readPreference + ", connPerHost: " + connectionsPerHost);
        List<MongoCredential> mongoCredentialList = new ArrayList<>();
        if (StringUtils.isNotEmpty(userName) && StringUtils.isNotEmpty(password)) {
            mongoCredentialList.add(MongoCredential.createCredential(userName, MongoDB.DB_ADMIN, password.toCharArray()));
        }
        MongoDB.connect(seedServers, mongoCredentialList, clientOptions); // to support development environment which may only have one mongo instance
    } catch (NumberFormatException e) {
                e.printStackTrace();
            }
    }



    public static void main(String[] args) {
        connectionWrapper();
        System.out.println("QA");
        DB db1 = MongoDB.getDB("1200000000", 1);
        System.out.println(db1.getCollection("targetings"));
//        createDatabaseConnection("","1200000000","".toCharArray());
//        getCollection("targetings");
//        getDocument();

        List<String> l = new ArrayList<String>();
        
    }
}
