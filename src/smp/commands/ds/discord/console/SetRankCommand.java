package smp.commands.ds.discord.console;

import org.javacord.api.event.message.MessageCreateEvent;
import smp.commands.ds.discord.DiscordCommand;
import smp.database.players.FindPlayerData;
import smp.models.PlayerData;
import smp.models.Rank;

import static smp.database.players.PlayerFunctions.updateData;
import static smp.database.ranks.FindRank.findRank;
import static smp.discord.Checks.rankCheck;

public class SetRankCommand extends DiscordCommand {
    public SetRankCommand() {
        super("setrank", " <rank> <playerID> -> Sets rank of the player.", 2,false, true);
    }

    @Override
    public void run(MessageCreateEvent listener) {
        String[] args = this.params;
        PlayerData plr = FindPlayerData.getPlayerDataAnyway(args[1]);
        Rank rank = findRank(args[0]);
        if (plr == null) {listener.getChannel().sendMessage("This player does not exist!"); return;}
        if (rank == null) {listener.getChannel().sendMessage("This rank does not exist!"); return;}
        if (!rankCheck(listener, plr, rank)) return;
        plr.set("rank", rank.rankId);
        updateData(plr);
        listener.getChannel().sendMessage("Rank of " + plr.name + " has been changed to " + plr.rank);
    }
}
