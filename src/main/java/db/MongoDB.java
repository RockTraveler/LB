package db;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.query.Query;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;

public class MongoDB {
    public static void main(String[] args) {
/*
        try {
              List<ServerAddress> addresses = new ArrayList<ServerAddress>();
              ServerAddress address1 = new ServerAddress("192.168.1.124" , 27001);
              ServerAddress address2 = new ServerAddress("192.168.1.124" , 27002);
              addresses.add(address1);
              addresses.add(address2);

              MongoClient client = new MongoClient(addresses);
              DB db = client.getDB( "coruscant" );
              DBCollection coll = db.getCollection( "Note" );
              DBCursor dbCursor=coll.find();
              System.out.println(dbCursor);
             

              BasicDBObject object = new BasicDBObject();
              object.append( "test2" , "testval2" );

               //读操作从副本节点读取
              ReadPreference preference = ReadPreference.secondary();
              DBObject dbObject = coll.findOne(object, null , preference);

              System.out.println(dbObject);


       } catch (Exception e) {
              e.printStackTrace();
       }*/
    	
        List<ServerAddress> addresses = new ArrayList<ServerAddress>();
        ServerAddress address1 = new ServerAddress("192.168.1.124" , 27001);
        ServerAddress address2 = new ServerAddress("192.168.1.124" , 27002);
        addresses.add(address1);
        addresses.add(address2);

        MongoClient client = new MongoClient(addresses);
        DB db = client.getDB( "testdb" );
        DBCollection coll = db.getCollection("testdb");
        DBCursor dbCursor=coll.find();
        BasicDBObject object = new BasicDBObject();
        object.append("1911","ripper");
        coll.save(object);
       long count=  dbCursor.getCollection().getCount();
        System.out.println(count);
}

}
