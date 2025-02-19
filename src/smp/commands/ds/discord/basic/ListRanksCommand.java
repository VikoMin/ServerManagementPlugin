package smp.commands.ds.discord.basic;

import org.javacord.api.event.message.MessageCreateEvent;
import smp.commands.ds.discord.DiscordCommand;
import smp.models.Rank;

import static smp.other.InitializeRanks.ranks;

public class ListRanksCommand extends DiscordCommand {
    public ListRanksCommand() {
        super("listranks", " -> Shows available ranks", 0);
    }

    @Override
    public void run(MessageCreateEvent listener) {
        StringBuilder builder = new StringBuilder();
        builder.append("```\nRanks: " + "\n");
        for (Rank rank : ranks){
            builder
                    .append("Rank ID: ").append(rank.rankId).append("\n")
                    .append("Rank Name: ").append(rank.rankName).append("\n")
                    .append("Rank Prefix: ").append(rank.rankPrefix).append("\n").append("\n");

        }
        builder.append("```");
        listener.getChannel().sendMessage(builder.toString());
    }
}
