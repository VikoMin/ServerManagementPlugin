package smp.database;

import arc.Core;
import arc.util.Log;
import com.mongodb.BasicDBObject;
import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import smp.models.PlayerData;
import smp.models.Rank;

import java.util.HashMap;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static com.mongodb.client.model.Filters.eq;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import static smp.system.reflect.ReflectSystem.getFieldParam;
import static smp.vars.Variables.mongoDbIP;

/**
 * DatabaseSystem is used for database functions and database initialization.
 * Most functions will use "Generics".
 */

public class DatabaseSystem {

    public static MongoClient mongoClient;
    public static MongoDatabase db;
    public static MongoCollection<PlayerData> playerCollection;
    public static MongoCollection<Rank> rankCollection;
    public static void initDatabase(){
        // Позволяет загружать свои модели в форме классов в коллекции
        CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
        CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));

        // Попытка подключения
        try {
            ConnectionString string = new ConnectionString(mongoDbIP);
            mongoClient = MongoClients.create(string);
        } catch (Exception e) {
            Log.err("There was a problem connecting to the database! Please, check your connection string" + e);
            Core.app.exit();
        }

        db = mongoClient.getDatabase("frostheaven").withCodecRegistry(pojoCodecRegistry);
        playerCollection = db.getCollection("players", PlayerData.class);
        rankCollection = db.getCollection("ranks", Rank.class);
    }

    public static <T> void updateDatabaseDocument(T data, MongoCollection<T> collection, String field, Object value){
        collection.replaceOne(eq(field, value), data, new ReplaceOptions().upsert(true));
    }

    public static <T> T findDatabaseDocument (MongoCollection<T> collection, HashMap<String, Object> fields){
        for (String field : fields.keySet()){
            T result = collection.find(Filters.eq(field, fields.get(field))).first();
            if (result == null) continue;
            return result;
        }
        return null;
    }

    public static <T> T findDatabaseDocument (MongoCollection<T> collection, HashMap<String, Object> fields, T orCreate){
        for (String field : fields.keySet()){
            T result = collection.find(Filters.eq(field, fields.get(field))).first();
            if (result == null) continue;
            return result;
        }
        return orCreate;
    }

    public static <T> int getOrder(MongoCollection<T> collection, String orderField){
        T data = collection.find().sort(new BasicDBObject(orderField, -1)).first();
        if (data == null) return 0;
        return (int) getFieldParam(orderField, data) + 1;
    }

    public static <T> int getOrder(MongoCollection<T> collection, String orderField, String classField){
        T data = collection.find().sort(new BasicDBObject(orderField, -1)).first();
        if (data == null) return 0;
        return (int) getFieldParam(classField, data) + 1;
    }
}
