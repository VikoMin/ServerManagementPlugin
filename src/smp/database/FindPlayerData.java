package smp.database;

import com.mongodb.client.model.Filters;
import mindustry.Vars;
import smp.models.PlayerData;


import java.util.regex.Pattern;

import static arc.util.Strings.canParseInt;
import static arc.util.Strings.parseInt;
import static com.mongodb.client.model.Filters.eq;
import static smp.database.InitializeDatabase.collection;
import static smp.functions.Utilities.notNullElse;

public class FindPlayerData {
    public static PlayerData getPlayerData(int id){
        return collection.find(eq("_id", id)).first();
    }
    public static PlayerData getPlayerData(String uuidOrName) {
        try {
            Pattern pattern = Pattern.compile(".?" + uuidOrName + ".?", Pattern.CASE_INSENSITIVE);
            return notNullElse(collection.find(Filters.eq("uuid", uuidOrName)).first(), collection.find(Filters.regex("name", pattern)).first());
        } catch (Exception e) {
            return notNullElse(collection.find(Filters.eq("uuid", uuidOrName)).first(), collection.find(Filters.eq("name", uuidOrName)).first());
        }
    }
    public static PlayerData getPlayerDataByIP(String ip){
        return collection.find(eq("ip", ip)).first();
    }
    public static PlayerData getPlayerDataAnyway(String uuidOrNameOrIDOrIp){
        if (canParseInt(uuidOrNameOrIDOrIp)){
            int i = parseInt(uuidOrNameOrIDOrIp);
            return collection.find(Filters.eq("_id", i)).first();
        } else {
            return notNullElse(getPlayerData(uuidOrNameOrIDOrIp), getPlayerDataByIP(uuidOrNameOrIDOrIp));
        }
    };
}
