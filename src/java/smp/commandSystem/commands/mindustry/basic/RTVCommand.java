package smp.commandSystem.commands.mindustry.basic;

import mindustry.Vars;
import mindustry.gen.Call;
import mindustry.gen.Groups;
import mindustry.gen.Player;
import mindustry.maps.Map;
import smp.commandSystem.mindustry.MindustryCommand;

import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

import static smp.Variables.*;
import static smp.functions.Utilities.voteCanceled;
import static smp.functions.Utilities.voteSuccess;

public class RTVCommand extends MindustryCommand<Player> {

    public RTVCommand() {
        super("rtv", "Rock the vote to change map!", new arc.struct.Seq<String>().add("<map...>"));
    }

    @Override
    public void run(String[] args, Player player) {
        String mapName = arc.util.Strings.stripColors(args[0]);

        final int[] votesRequired = new int[1];
        AtomicInteger time = new AtomicInteger(60);
        votesRequired[0] = (int) Math.ceil((double) Groups.player.size()/2);
        java.util.Timer timer = new java.util.Timer();
        if (isVoting){
            player.sendMessage("Vote is already running!");
            return;
        }
        Map choosedMap = Vars.maps.customMaps().find(map -> map.name().contains(mapName));
        if (choosedMap == null){
            player.sendMessage("Could not find that map!");
            return;
        }
        Call.sendMessage(player.name() + "[white] Started vote for map " + choosedMap.plainName() + " -> " + votes.get() +"/"+ votesRequired[0] + ", y/n to vote");
        isVoting = true;
        timer.schedule((new TimerTask() {
            @Override
            public void run() {
                time.getAndAdd(-1);
                votesRequired[0] = (int) Math.ceil((double) Groups.player.size() / 2);
                if (votes.get() >= votesRequired[0]) {
                    voteSuccess(choosedMap);
                    isVoting = false;
                    timer.cancel();
                }
                if (time.get() < 0) {
                    voteCanceled();
                    isVoting = false;
                    timer.cancel();
                }
            }
        }), 0, 1000);
    }
}
