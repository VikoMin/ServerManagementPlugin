package smp.commands.discord;

import org.javacord.api.event.message.MessageCreateEvent;
import smp.database.players.FindPlayerData;
import smp.models.PlayerData;
import smp.models.Rank;

import static arc.util.Strings.canParseInt;
import static java.lang.Integer.parseInt;
import static smp.database.InitializeDatabase.rankCollection;
import static smp.database.players.PlayerFunctions.updateData;
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
