package smp.database;

import arc.struct.ObjectSet;
import arc.util.Log;
import arc.util.Timer;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import mindustry.Vars;
import mindustry.gen.Groups;
import mindustry.gen.Player;
import mindustry.net.Administration;
import mindustry.net.NetConnection;
import smp.models.PlayerData;

import java.util.Objects;

import static com.mongodb.client.model.Filters.eq;
import static smp.database.FindPlayerData.getPlayerData;
import static smp.database.FindPlayerData.getPlayerDataByIP;
import static smp.database.InitializeDatabase.collection;

public class PlayerChecks {
    public static void MongoDbPlayerRankCheck(String uuid) {
        Player eventPlayer = Groups.player.find(p -> p.uuid().contains(uuid));
        if (eventPlayer == null) return;
        PlayerData data = getPlayerData(uuid);
        String tempName = data.rawName;
        if (!Objects.equals(data.customPrefix, "<none>")){
            eventPlayer.name = data.customPrefix + " [" + "#" + eventPlayer.color.toString() + "]" + tempName;
        } else {
            switch (data.rank) {
                case "player" -> eventPlayer.name = "[white]<P>" +" [" + "#" + eventPlayer.color.toString() + "]" + tempName;
                case "trusted" -> eventPlayer.name = "[blue]<T>" +" [" + "#" + eventPlayer.color.toString() + "]" + tempName;
                case "admin" -> eventPlayer.name = "[#f]<A>" +" [" + "#" + eventPlayer.color.toString() + "]" + tempName;
                case "console" -> eventPlayer.name = "[purple]<C>" +" [" + "#" + eventPlayer.color.toString() + "]" + tempName;
                case "owner" -> eventPlayer.name = "[cyan]<O>" +" [" + "#" + eventPlayer.color.toString() + "]" + tempName;
            }
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
    public static void MongoDbPlaytimeTimer(){
        Timer.schedule(() -> {
            for (Player player : Groups.player){
                PlayerData data = getPlayerData(player.uuid());
                if (data == null) return;
                data.playtime += 1;
                MongoDbUpdate(data);
            }
        }, 0, 60);
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
