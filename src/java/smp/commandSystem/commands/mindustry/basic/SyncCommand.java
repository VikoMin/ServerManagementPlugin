package smp.commandSystem.commands.mindustry.basic;

import arc.util.Time;
import mindustry.gen.Call;
import mindustry.gen.Player;
import smp.commandSystem.mindustry.MindustryCommand;

import static mindustry.Vars.netServer;

public class SyncCommand extends MindustryCommand<Player> {
    public SyncCommand() {
        super("sync", "Re-synchronize world state.");
    }

    @Override
    public void run(String[] args, Player player) {
        if(player.isLocal()){
            player.sendMessage("[scarlet]Re-synchronizing as the host is pointless.");
        }else{
            if(Time.timeSinceMillis(player.getInfo().lastSyncTime) < 1000 * 5){
                player.sendMessage("[scarlet]You may only /sync every 5 seconds.");
                return;
            }

            player.getInfo().lastSyncTime = Time.millis();
            Call.worldDataBegin(player.con);
            netServer.sendWorldData(player);
        }
    }
}
