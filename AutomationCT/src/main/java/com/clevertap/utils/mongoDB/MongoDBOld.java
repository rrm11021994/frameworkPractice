package com.clevertap.utils.mongoDB;

import com.mongodb.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class MongoDBOld {

        public static void main(String[] args) throws IOException {


            Properties prop=new Properties();
            FileInputStream fis = new FileInputStream("./config.properties");
            prop.load(fis);
//        addressList.add(new ServerAddress(prop.getProperty("MongoHost"),prop.getProperty("MongoPost")));
//        addressList.add(prop.getProperty("MongoPort"));


//        MongoClient mongoClient=new MongoClient(prop.getProperty("MongoHost"),Integer.parseInt(prop.getProperty("MongoPort"))); /*connects to default port 27017*/

            Mongo mongo = new Mongo();
            DB mongoDatabase= mongo.getDB("1200000000");
            DBCollection mongoCollection= mongoDatabase.getCollection("daily_stats");

//        BasicDBObject query=new BasicDBObject("_id","20190305");
            DBCursor mongoCursor= mongoCollection.find(new BasicDBObject(), new BasicDBObject().append("cnt.p.Android",1));

            for (DBObject obj : mongoCursor) {
                BasicDBObject basicDBObject = (BasicDBObject) obj;
                System.out.println(basicDBObject);
            }









        /* below code to print all data base name
//        MongoCursor<String> cursor=mongoClient.listDatabaseNames().iterator();
//        while (cursor.hasNext()){
//            System.out.println(cursor.next());
//        }

*/

        /* it gives the name of all collections
        MongoDatabase mongoDatabase=mongoClient.getDatabase("1200000000");
        MongoCursor<String> col=mongoDatabase.listCollectionNames().iterator();
        while (col.hasNext()){
            System.out.println(col.next());
        }

*/

        /* to list down all the documnets in a specific collection
        MongoDatabase mongoDatabase=mongoClient.getDatabase("1200000000");
        MongoCollection mongoCollection=mongoDatabase.getCollection("daily_stats");
        MongoCursor<Document> documentMongoCursor=mongoCollection.find().iterator();
        while (documentMongoCursor.hasNext()){
            System.out.println(documentMongoCursor.next());
        }

        */



//        MongoDatabase mongoDatabase=mongoClient.getDatabase("1200000000");
//        MongoCollection mongoCollection=mongoDatabase.getCollection("daily_stats");
//        MongoCursor<Document> documentMongoCursor=mongoCollection.find().iterator();
//        while (documentMongoCursor.hasNext()){
//            Document document=documentMongoCursor.next();
//            BasicDBObject basicDBObject=new BasicDBObject(document);
////            System.out.println(basicDBObject.get("_id") +" : "+basicDBObject.get("cnt"));
////            Object obj=basicDBObject.get("cnt.p");
//
//            System.out.println(new BasicDBObject((Document)basicDBObject.get("cnt")).get("p"));
//
//
//
//        }


//        DB mongoDatabase= (DB) mongoClient.getDatabase("1200000000");










        /* This is working code. Do not disturb

//        MongoDatabase mongoDatabase=mongoClient.getDatabase("1200000000");
//        MongoCollection mongoCollection=mongoDatabase.getCollection("daily_stats");
//
////        BasicDBObject query=new BasicDBObject("cnt",new BasicDBObject("$gt",5)); // need to uderstand how it works
//
//        FindIterable<Document> docs=mongoCollection.find();
//        MongoCursor<Document> cursor=docs.iterator();
//
//        while (cursor.hasNext()){
//            Document document=cursor.next();
//            System.out.println(document.toJson());
////            List list=new ArrayList(document.values());
////            System.out.println(list.get(0));
////            System.out.print(" :- ");
////            System.out.println(list.get(1));
////            System.out.print(" :- ");
////            System.out.print(list.get(2));
//
//            BasicDBObject x = new BasicDBObject(document);
//            System.out.println(x.get("cnt"));
//            System.out.println((new BasicDBObject((Document) x.get("cnt"))).get("p"));
//            System.out.println(new BasicDBObject((Document)new BasicDBObject((Document) x.get("cnt")).get("p")).get("Android"));
//            System.out.println(new BasicDBObject((Document)new BasicDBObject((Document) x.get("cnt")).get("p")).get("iOS"));
//            System.out.println((new BasicDBObject((Document) x.get("cnt"))).get("t"));
//
//
//
//        }

//        for (Document document:docs){
//////            System.out.println(document.getInteger("_id"));
//////            System.out.println(document.entrySet());
//////            System.out.println(document.toJson());
////
////           if( document.getInteger("_id")==20190301){
////               System.out.println(document.entrySet());
////
////
////           }
////
////
//
////            for (Map.Entry<String,BsonValue> entr: document.entrySet()){
////
////            }
//
//
//        }

*/





        }



    }

