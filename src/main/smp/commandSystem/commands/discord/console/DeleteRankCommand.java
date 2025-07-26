package smp.commandSystem.commands.discord.console;

import com.mongodb.client.model.Filters;
import org.javacord.api.event.message.MessageCreateEvent;
import smp.commandSystem.discord.DiscordCommand;
import smp.models.Rank;

import static java.lang.Integer.parseInt;
import static smp.database.DatabaseSystem.findDatabaseDocument;
import static smp.database.DatabaseSystem.rankCollection;
import static smp.Utilities.createHashMap;
import static smp.system.other.InitializeRanks.initializeRanks;
import static smp.system.other.InitializeRanks.ranks;

public class DeleteRankCommand extends DiscordCommand {
    public DeleteRankCommand() {
        super("deleterank", " <id> -> Deletes existing rank.", 1,false, true);
    }

    @Override
    public void run(MessageCreateEvent listener) {
        String[] args = this.params;
        Rank rank = findDatabaseDocument(rankCollection, createHashMap("id", args[0]));
        if (rank != null) {
            rankCollection.deleteOne(Filters.eq("rankId", rank.id));
            ranks.remove(rank);
            listener.getChannel().sendMessage("Rank has been deleted!");
            initializeRanks();
        } else {
            listener.getChannel().sendMessage("Could not find that rank!");
        }
    }
}
