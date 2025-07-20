package smp.commandSystem.commands.discord.console;

import org.javacord.api.event.message.MessageCreateEvent;
import smp.commandSystem.discord.DiscordCommand;
import smp.models.Rank;

import static arc.util.Strings.canParseInt;
import static java.lang.Integer.parseInt;
import static smp.database.DatabaseSystem.rankCollection;
import static smp.other.InitializeRanks.ranks;

public class CreateRankCommand extends DiscordCommand {
    public CreateRankCommand() {
        super("createrank", " <id> <name> <prefix> <priority> -> Creates new rank.", 4,false, true);
    }

    @Override
    public void run(MessageCreateEvent listener) {
        String[] args = this.params;

        if (!canParseInt(args[3])) { listener.getChannel().sendMessage("Priority should be a number!"); return;}

        Rank rank = new Rank(args[0], args[1], args[2], parseInt(args[3]));
        rankCollection.insertOne(rank);
        ranks.add(rank);

        listener.getChannel().sendMessage("Rank has been created!");
    }
}
