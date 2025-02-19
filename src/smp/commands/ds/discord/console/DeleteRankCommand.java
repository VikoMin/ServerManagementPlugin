package smp.commands.ds.discord.console;

import com.mongodb.client.model.Filters;
import org.javacord.api.event.message.MessageCreateEvent;
import smp.commands.ds.discord.DiscordCommand;
import smp.models.Rank;

import static java.lang.Integer.parseInt;
import static smp.database.InitializeDatabase.rankCollection;
import static smp.database.ranks.FindRank.findRank;
import static smp.other.InitializeRanks.initializeRanks;
import static smp.other.InitializeRanks.ranks;

public class DeleteRankCommand extends DiscordCommand {
    public DeleteRankCommand() {
        super("deleterank", " <id> -> Creates new rank.", 1,false, true);
    }

    @Override
    public void run(MessageCreateEvent listener) {
        String[] args = this.params;
        Rank rank = findRank(args[0]);
        if (rank != null) {
            rankCollection.deleteOne(Filters.eq("rankId", rank.rankId));
            ranks.remove(rank);
            listener.getChannel().sendMessage("Rank has been deleted!");
            initializeRanks();
        } else {
            listener.getChannel().sendMessage("Could not find that rank!");
        }
    }
}
