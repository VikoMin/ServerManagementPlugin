package smp.database.players;

import arc.util.Timer;
import mindustry.gen.Groups;
import mindustry.gen.Player;
import smp.models.PlayerData;

import static smp.database.players.FindPlayerData.getPlayerData;
import static smp.database.players.PlayerFunctions.updateData;

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
