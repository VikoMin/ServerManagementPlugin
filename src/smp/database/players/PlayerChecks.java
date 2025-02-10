package smp.database.players;

import arc.util.Log;
import arc.util.Timer;
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
import static smp.database.players.FindPlayerData.getPlayerData;
import static smp.database.players.FindPlayerData.getPlayerDataByIP;
import static smp.database.InitializeDatabase.collection;
import static smp.database.ranks.FindRank.findRank;

public class PlayerChecks {
    public static void MongoDbPlayerRankCheck(String uuid) {
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

    public static void MongoDbPlayerIpCheck(NetConnection player){
        PlayerData data = getPlayerDataByIP(player.address);
        if (data == null) {
            return;
        }
        data.ip = player.address;
        MongoDbUpdate(data);
    }
    public static <T> void MongoDbUpdate(PlayerData data){
        collection.replaceOne(eq("_id", data.id), data, new ReplaceOptions().upsert(true));
    }
    public static void MongoDbCheck(){
        try (MongoCursor<PlayerData> cursor = collection.find(Filters.gte("playtime", 2250)).iterator()) {
            while (cursor.hasNext()) {
                PlayerData csr = cursor.next();
                String activeAch = "[lime]Hyper[green]active";
                if (!csr.achievements.contains(activeAch)) {
                    Log.debug(csr.name);
                    csr.achievements.add(activeAch);
                    MongoDbUpdate(csr);
                }
            }
        }
        try (MongoCursor<PlayerData> cursor = collection.find(Filters.lte("_id", 150)).iterator()) {
            while (cursor.hasNext()) {
                PlayerData csr = cursor.next();
                String activeAch = "[orange]Vete[yellow]ran";
                if (!csr.achievements.contains(activeAch)) {
                    Log.debug(csr.name);
                    csr.achievements.add(activeAch);
                    MongoDbUpdate(csr);
                }
            }
        }
        }
}
