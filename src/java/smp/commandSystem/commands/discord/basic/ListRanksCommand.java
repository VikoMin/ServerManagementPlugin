package smp.commandSystem.commands.discord.basic;

import org.javacord.api.event.message.MessageCreateEvent;
import smp.commandSystem.discord.DiscordCommand;
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
                    .append("Rank ID: ").append(rank.id).append("\n")
                    .append("Rank name: ").append(rank.name).append("\n")
                    .append("Rank priority: ").append(rank.priotity).append("\n")
                    .append("Rank prefix: ").append(rank.prefix).append("\n").append("\n");

        }
        builder.append("```");
        listener.getChannel().sendMessage(builder.toString());
    }
}
