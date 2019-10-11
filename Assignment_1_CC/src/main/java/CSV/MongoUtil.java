package CSV;

import com.mongodb.MongoClient;
import com.mongodb.client.*;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.HashMap;
import java.util.Map;

public class MongoUtil {

    private MongoDatabase mongoDatabase;
    Map<String, MongoCollection<Document>> collectionMap;
    MongoCursor cursor = null;

    public MongoUtil(String dbName){
        MongoClient mongoClient = new MongoClient();
        mongoDatabase = mongoClient.getDatabase(dbName);
        collectionMap = new HashMap<String, MongoCollection<Document>>();
    }

    public void insert(String colName, Document doc){
        MongoCollection<Document> collection = collectionMap.get(colName);
        if (collection == null){
            collection = mongoDatabase.getCollection(colName);
            collectionMap.put(colName, collection);
        }
        collection.insertOne(doc);
    }

    public Document selectNext(String colName){
        if (cursor == null){
            MongoCollection<Document> collection = mongoDatabase.getCollection(colName);
            FindIterable findIterable = collection.find();
            cursor = findIterable.iterator();
        }
        if (cursor.hasNext()){
            return (Document) cursor.next();
        }
        return null;
    }
}
