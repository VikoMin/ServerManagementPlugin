package smp.database;

import arc.Core;
import arc.Settings;
import arc.util.Log;
import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import smp.models.PlayerData;
import smp.models.Setting;

import java.util.Scanner;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class InitializeDatabase {

    public static MongoClient mongoClient;
    public static MongoDatabase db;
    public static MongoCollection<PlayerData> collection;
    public static MongoCollection<Setting> settingCollection;
    public static String connectionString = "";

    public static void initDatabase(){
        CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
        CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
        Scanner inp = new Scanner(System.in);
        if (Core.settings.getString("mongodbip") == null) {
            System.out.println("Please, print mongoDB IP: ");
            Core.settings.put("mongodbip", inp.next());
        }
        if (connectionString.replace(" ", "") == ""){
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
        collection = db.getCollection("newplayers", PlayerData.class);
        settingCollection = db.getCollection("settings", Setting.class);
    }
}
