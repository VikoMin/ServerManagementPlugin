package smp.events;

import arc.util.Log;
import mindustry.gen.Call;
import mindustry.gen.Player;
import smp.models.PlayerData;

import static smp.database.players.PlayerFunctions.findPlayerDataOrCreate;
import static smp.history.History.historyPlayers;

public class PlayerLeaveEvent {
    public static void leaveEvent(Player player){
        PlayerData data = findPlayerDataOrCreate(player);
        Call.sendMessage(player.name + " [gray][" + data.id + "]" + " [red]left the server!");
        Log.info(player.plainName() + " left the server!");
        if (historyPlayers.contains(player)){
            historyPlayers.remove(player);
        }
    }
}
