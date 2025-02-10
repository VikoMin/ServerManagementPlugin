package smp.database.players;

import com.mongodb.BasicDBObject;
import com.mongodb.client.model.ReplaceOptions;
import mindustry.gen.Player;
import smp.models.PlayerData;

import java.util.Optional;

import static com.mongodb.client.model.Filters.eq;
import static smp.database.InitializeDatabase.collection;


public class PlayerFunctions {
    public static PlayerData findPlayerDataOrCreate(Player eventPlayer){
        return Optional.ofNullable(collection.find(eq("uuid", eventPlayer.uuid())).first()).orElse(
                new PlayerData(eventPlayer.uuid(), getNextID())
        );
    }

    public static void fillData(PlayerData data, Player plr){
        data.name = plr.plainName();
        data.rawName = plr.name();
        data.ip = plr.con.address;
        data.uuid = plr.uuid();
        collection.replaceOne(eq("_id", data.id), data, new ReplaceOptions().upsert(true));
    }
    public static void updateData(PlayerData oldData, PlayerData newData){
        collection.replaceOne(eq("_id", oldData.id), newData, new ReplaceOptions().upsert(true));
    }
    public static void updateData(PlayerData data){
        collection.replaceOne(eq("_id", data.id), data, new ReplaceOptions().upsert(true));
    }

    public static int getNextID(){
        PlayerData data = collection.find().sort(new BasicDBObject("_id", -1)).first();
        if (data == null) return 0;
        return data.id + 1;
    }
}
