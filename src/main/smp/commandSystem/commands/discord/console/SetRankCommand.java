package smp.commandSystem.commands.discord.console;

import org.javacord.api.event.message.MessageCreateEvent;
import smp.commandSystem.discord.DiscordCommand;
import smp.database.players.FindPlayerData;
import smp.models.PlayerData;
import smp.models.Rank;

import static smp.database.DatabaseSystem.*;
import static smp.discord.DiscordChecks.hasRank;
import static smp.Utilities.createHashMap;

public class SetRankCommand extends DiscordCommand {
    public SetRankCommand() {
        super("setrank", " <rank> <playerID> -> Sets rank of the player.", 2,false, true);
    }

    @Override
    public void run(MessageCreateEvent listener) {
        String[] args = this.params;
        PlayerData data = FindPlayerData.getPlayerDataAnyway(args[1]);
        Rank rank = findDatabaseDocument(rankCollection, createHashMap("id", args[0]));
        if (data == null) {listener.getChannel().sendMessage("This player does not exist!"); return;}
        if (rank == null) {listener.getChannel().sendMessage("This rank does not exist!"); return;}
        if (!hasRank(listener, data, rank)) return;
        data.set("rank", rank.id);
        updateDatabaseDocument(data, playerCollection, "_id", data.id);
        listener.getChannel().sendMessage("Rank of " + data.name + " has been changed to " + data.rank);
    }
}
