package smp.database.players;

import arc.util.Timer;
import mindustry.gen.Groups;
import mindustry.gen.Player;
import mindustry.net.NetConnection;
import smp.models.PlayerData;
import smp.models.Rank;

import java.util.Objects;

import static smp.database.DatabaseSystem.*;
import static smp.database.players.FindPlayerData.getPlayerData;
import static smp.database.players.FindPlayerData.getPlayerDataByIP;
import static smp.functions.Utilities.createHashMap;

/**
 This system contains ONLY player or playerdata-related functions that interact with database
 */

public class PlayerChecks {
    public static void displayPlayerRank(String uuid) {
        Player eventPlayer = Groups.player.find(p -> p.uuid().contains(uuid));

        if (eventPlayer == null) return;

        PlayerData data = getPlayerData(uuid);
        Rank rank = findDatabaseDocument(rankCollection, createHashMap("id", data.rank));
        String tempName = data.rawName;

        if (rank == null) return;

        if (!Objects.equals(data.customPrefix, "<none>")){
            eventPlayer.name = data.customPrefix + " [" + "#" + eventPlayer.color.toString() + "]" + tempName;
        } else {
            eventPlayer.name = rank.prefix +" [" + "#" + eventPlayer.color.toString() + "]" + tempName;
        }
    }

    public static void checkPlayerIP(NetConnection player){
        PlayerData data = getPlayerDataByIP(player.address);
        if (data == null) {
            return;
        }
        data.ip = player.address;
        updateDatabaseDocument(data, playerCollection, "_id", data.id);
    }

    public static void initializeCounter(){
        Timer.schedule(() -> {
            for (Player player : Groups.player){
                PlayerData data = getPlayerData(player.uuid());
                if (data == null) return;
                data.playtime += 1;
                updateDatabaseDocument(data, playerCollection, "_id", getPlayerData(player.uuid()).id);
            }
        }, 0, 60);
    }
}
