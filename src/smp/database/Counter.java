package smp.database;

import arc.util.Log;
import arc.util.Timer;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import mindustry.gen.Groups;
import mindustry.gen.Player;
import smp.models.PlayerData;

import static smp.database.FindPlayerData.getPlayerData;
import static smp.database.PlayerFunctions.updateData;

public class Counter {
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
