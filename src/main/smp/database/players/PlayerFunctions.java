package smp.database.players;

import com.mongodb.client.model.ReplaceOptions;
import mindustry.gen.Player;
import smp.models.PlayerData;

import java.util.Optional;

import static com.mongodb.client.model.Filters.eq;
import static smp.database.DatabaseSystem.getOrder;
import static smp.database.DatabaseSystem.playerCollection;


public class PlayerFunctions {
    public static PlayerData findPlayerDataOrCreate(Player eventPlayer){
        return Optional.ofNullable(playerCollection.find(eq("uuid", eventPlayer.uuid())).first()).orElse(
                new PlayerData(eventPlayer.uuid(), getOrder(playerCollection, "_id", "id"))
        );
    }

    public static void fillData(PlayerData data, Player plr){
        data.name = plr.plainName();
        data.rawName = plr.name();
        data.ip = plr.con.address;
        data.uuid = plr.uuid();
        playerCollection.replaceOne(eq("_id", data.id), data, new ReplaceOptions().upsert(true));
    }
}
