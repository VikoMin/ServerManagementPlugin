package smp.commands.discord;

import org.javacord.api.event.message.MessageCreateEvent;
import smp.database.players.FindPlayerData;
import smp.models.PlayerData;

import static smp.database.players.PlayerFunctions.updateData;

public class SetRankCommand extends DiscordCommand {
    public SetRankCommand() {
        super("setrank", " <rank> <playerID> -> Sets rank of the player.", 2,false, true);
    }

    @Override
    public void run(MessageCreateEvent listener) {
        String[] args = this.params;
        PlayerData plr = FindPlayerData.getPlayerData(args[1]);
        if (plr == null) {listener.getChannel().sendMessage("This player does not exist!"); return;}
        plr.changeValue("rank", args[0]);
        updateData(FindPlayerData.getPlayerData(args[1]), plr);
        listener.getChannel().sendMessage("Rank of " + plr.name + " has been changed to " + plr.rank);
    }
}
