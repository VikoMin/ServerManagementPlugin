package smp.commands.basic;

import mindustry.core.NetServer;
import mindustry.gen.Groups;
import mindustry.gen.Player;
import mindustry.net.Administration;
import smp.commands.BasicCommand;
import static smp.Variables.admins;

import static smp.Variables.chatFormatter;
import static smp.errors.MindustryErrors.noPlayerFound;
import static smp.functions.FindPlayer.findPlayerByName;

public class TeamChatCommand extends BasicCommand<Player> {

    public TeamChatCommand() {
        super("t", "Sends message to your team only", new arc.struct.Seq<String>().add("<message...>"));
    }

    @Override
    public void run(String[] args, Player player) {
        String message = admins.filterMessage(player, args[0]);
        if(message != null){
            String raw = "[#" + player.team().color.toString() + "]<T> " + chatFormatter.format(player, message);
            Groups.player.each(p -> p.team() == player.team(), o -> o.sendMessage(raw, player, message));
        }
    }
}
