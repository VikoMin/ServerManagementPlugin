package smp.commandSystem.commands.mindustry.basic;

import arc.Events;
import mindustry.game.EventType;
import mindustry.game.Team;
import mindustry.gen.Call;
import mindustry.gen.Groups;
import mindustry.gen.Player;
import smp.commandSystem.mindustry.MindustryCommand;

import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

import static smp.vars.Variables.*;

public class RTVCommand extends MindustryCommand<Player> {

    public RTVCommand() {
        super("rtv", "Rock the vote to change map!");
    }

    @Override
    public void run(String[] args, Player player) {
        final int[] votesRequired = new int[1];
        AtomicInteger time = new AtomicInteger(60);
        votesRequired[0] = (int) Math.ceil((double) Groups.player.size()/2);
        java.util.Timer timer = new java.util.Timer();
        if (isVoting){
            player.sendMessage("Vote is already running!");
            return;
        }
        Call.sendMessage(player.name() + "[white] Started vote for map skip " + " -> " + votes.get() +"/"+ votesRequired[0] + ", y/n to vote");
        isVoting = true;
        timer.schedule((new TimerTask() {
            @Override
            public void run() {
                time.getAndAdd(-1);
                votesRequired[0] = (int) Math.ceil((double) Groups.player.size() / 2);
                if (votes.get() >= votesRequired[0]) {
                    voteSuccess();
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

    public void voteCanceled(){
        Call.sendMessage("[red]Vote has been canceled!");
        votes.set(0);
        votedPlayer.clear();
    }

    public void voteSuccess(){
        Call.sendMessage("[green]Vote success! Changing map!");
        Events.fire(new EventType.GameOverEvent(Team.derelict));
        votes.set(0);
        votedPlayer.clear();
    }
}
