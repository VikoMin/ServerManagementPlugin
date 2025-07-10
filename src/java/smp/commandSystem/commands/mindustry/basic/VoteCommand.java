package smp.commandSystem.commands.mindustry.basic;

import arc.util.Strings;
import mindustry.gen.Call;
import mindustry.gen.Player;
import smp.commandSystem.mindustry.MindustryCommand;

import static smp.Variables.*;

public class VoteCommand extends MindustryCommand<Player> {

    public VoteCommand() {
        super("vote", "Vote to kick the current player. [red]Admin can cancel the voting with 'c'.", new arc.struct.Seq<String>().add("<y/n/c>"));
    }

    @Override
    public void run(String[] args, Player player) {
        if(currentlyKicking == null){
            player.sendMessage("[scarlet]Nobody is being voted on.");
        }else{
            if(player.admin && args[0].equalsIgnoreCase("c")){
                Call.sendMessage(Strings.format("[lightgray]Vote canceled by admin[orange] @[lightgray].", player.name));
                currentlyKicking.task.cancel();
                currentlyKicking = null;
                return;
            }

            if(player.isLocal()){
                player.sendMessage("[scarlet]Local players can't vote. Kick the player yourself instead.");
                return;
            }

            int sign = switch(args[0].toLowerCase()){
                case "y", "yes" -> 1;
                case "n", "no" -> -1;
                default -> 0;
            };

            //hosts can vote all they want
            if((currentlyKicking.voted.get(player.uuid(), 2) == sign || currentlyKicking.voted.get(admins.getInfo(player.uuid()).lastIP, 2) == sign)){
                player.sendMessage(Strings.format("[scarlet]You've already voted @. Sit down.", args[0].toLowerCase()));
                return;
            }

            if(currentlyKicking.target == player){
                player.sendMessage("[scarlet]You can't vote on your own trial.");
                return;
            }

            if(currentlyKicking.target.team() != player.team()){
                player.sendMessage("[scarlet]You can't vote for other teams.");
                return;
            }

            if(sign == 0){
                player.sendMessage("[scarlet]Vote either 'y' (yes) or 'n' (no).");
                return;
            }

            currentlyKicking.vote(player, sign);
        }
    }
}
