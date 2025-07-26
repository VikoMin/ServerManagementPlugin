package smp.database.players;

import com.mongodb.client.model.Filters;
import smp.models.PlayerData;


import java.util.regex.Pattern;

import static arc.util.Strings.canParseInt;
import static arc.util.Strings.parseInt;
import static com.mongodb.client.model.Filters.eq;
import static smp.database.DatabaseSystem.playerCollection;
import static smp.Utilities.notNullElse;

public class FindPlayerData {
    public static PlayerData getPlayerData(int id){
        return playerCollection.find(eq("_id", id)).first();
    }
    public static PlayerData getPlayerData(String uuidOrName) {
        try {
            Pattern pattern = Pattern.compile(".?" + uuidOrName + ".?", Pattern.CASE_INSENSITIVE);
            return notNullElse(playerCollection.find(Filters.eq("uuid", uuidOrName)).first(), playerCollection.find(Filters.regex("name", pattern)).first());
        } catch (Exception e) {
            return notNullElse(playerCollection.find(Filters.eq("uuid", uuidOrName)).first(), playerCollection.find(Filters.eq("name", uuidOrName)).first());
        }
    }
    public static PlayerData getPlayerDataByIP(String ip){
        return playerCollection.find(eq("ip", ip)).first();
    }
    public static PlayerData getPlayerDataByDiscordID(long discordID){
        return playerCollection.find(eq("discordId", discordID)).first();
    }
    public static PlayerData getPlayerDataAnyway(String uuidOrNameOrIDOrIp){
        if (canParseInt(uuidOrNameOrIDOrIp)){
            int i = parseInt(uuidOrNameOrIDOrIp);
            return playerCollection.find(Filters.eq("_id", i)).first();
        } else {
            return notNullElse(getPlayerData(uuidOrNameOrIDOrIp), getPlayerDataByIP(uuidOrNameOrIDOrIp));
        }
    };
}
