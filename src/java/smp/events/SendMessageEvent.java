package smp.events;

import mindustry.gen.Call;
import mindustry.gen.Player;

import static smp.Variables.*;

public class SendMessageEvent {
    public static void rtvEvent(String message, Player player){
        if (!isVoting) return;
        if ((message == "y" || message == "n") && votedPlayer.contains(player)) return;

        if (message == "y"){
            Call.sendChatMessage("[green]" + player.plainName() + " voted for map change!");
            votedPlayer.add(player);
            votes.set(votes.get() + 1);
        }
        if (message == "n"){
            Call.sendChatMessage("[red]" + player.plainName() + " voted against map change!");
            votedPlayer.add(player);
            votes.set(votes.get() - 1);
        }
    }
}
