package smp.database;

import arc.Core;
import arc.util.Log;
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
import smp.models.Setting;

import java.util.HashMap;
import java.util.Scanner;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static com.mongodb.client.model.Filters.eq;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

/**
 * DatabaseSystem is used for database functions and database initialization.
 * Most functions will use "Generics".
 */

public class DatabaseSystem {

    public static MongoClient mongoClient;
    public static MongoDatabase db;
    public static MongoCollection<PlayerData> playerCollection;
    public static MongoCollection<Setting> settingCollection;
    public static MongoCollection<Rank> rankCollection;
    public static String connectionString = " ";

    public static void initDatabase(){
        // Позволяет загружать свои модели в форме классов в коллекции
        CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
        CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));

        // Попытка подключения
        Scanner inp = new Scanner(System.in);
        if (Core.settings.getString("mongodbip") == null) {
            System.out.println("Please, print mongoDB IP: ");
            Core.settings.put("mongodbip", inp.next());
        }
        if (connectionString.equals(" ")){
            connectionString = Core.settings.getString("mongodbip");
        }
        try {
            ConnectionString string = new ConnectionString(connectionString);
            mongoClient = MongoClients.create(string);
        } catch (Exception e) {
            Log.err("There was a problem connecting to the database! Please, check your connection string" + e);
            Core.settings.remove("mongodbip");
            Core.app.exit();
        }

        db = mongoClient.getDatabase("mindustry").withCodecRegistry(pojoCodecRegistry);
        playerCollection = db.getCollection("players", PlayerData.class);
        settingCollection = db.getCollection("settings", Setting.class);
        rankCollection = db.getCollection("ranks", Rank.class);
    }

    public static <T> void UpdateDatabaseDocument(T data, MongoCollection<T> collection, String A, Object B){
        collection.replaceOne(eq(A, B), data, new ReplaceOptions().upsert(true));
    }

    public static <T> T FindDatabaseDocument (MongoCollection<T> collection, HashMap<String, Object> fields){
        for (String field : fields.keySet()){
            T result = collection.find(Filters.eq(field, fields.get(field))).first();
            if (result == null) continue;
            return result;
        }
        return null;
    }

    public static <T> T FindDatabaseDocument (MongoCollection<T> collection, HashMap<String, Object> fields, T orCreate){
        for (String field : fields.keySet()){
            T result = collection.find(Filters.eq(field, fields.get(field))).first();
            if (result == null) continue;
            return result;
        }
        return orCreate;
    }
}
