package smp.database.players;

import arc.util.Log;
import arc.util.Timer;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import mindustry.gen.Groups;
import mindustry.gen.Player;
import mindustry.net.NetConnection;
import smp.models.PlayerData;
import smp.models.Rank;

import java.util.Objects;

import static com.mongodb.client.model.Filters.eq;
import static smp.database.DatabaseSystem.*;
import static smp.database.players.FindPlayerData.getPlayerData;
import static smp.database.players.FindPlayerData.getPlayerDataByIP;
import static smp.database.players.PlayerFunctions.updateData;
import static smp.database.ranks.FindRank.findRank;

/**
 This system contains ONLY player or playerdata-related functions that interact with database
 */

public class PlayerChecks {
    public static void DisplayPlayerRank(String uuid) {
        Player eventPlayer = Groups.player.find(p -> p.uuid().contains(uuid));

        if (eventPlayer == null) return;

        PlayerData data = getPlayerData(uuid);
        Rank rank = findRank(data.rank);
        String tempName = data.rawName;

        if (rank == null) return;

        if (!Objects.equals(data.customPrefix, "<none>")){
            eventPlayer.name = data.customPrefix + " [" + "#" + eventPlayer.color.toString() + "]" + tempName;
        } else {
            eventPlayer.name = rank.rankPrefix +" [" + "#" + eventPlayer.color.toString() + "]" + tempName;
        }
    }

    public static void CheckPlayerIP(NetConnection player){
        PlayerData data = getPlayerDataByIP(player.address);
        if (data == null) {
            return;
        }
        data.ip = player.address;
        UpdateDatabaseDocument(data, playerCollection, "_id", data.id);
    }

    public static void initializeCounter(){
        Timer.schedule(() -> {
            for (Player player : Groups.player){
                PlayerData data = getPlayerData(player.uuid());
                if (data == null) return;
                data.playtime += 1;
                updateData(getPlayerData(player.uuid()), data);
            }
        }, 0, 60);
    }
}
