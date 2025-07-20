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
                    .append("Rank Name: ").append(rank.name).append("\n")
                    .append("Rank Prefix: ").append(rank.prefix).append("\n").append("\n");

        }
        builder.append("```");
        listener.getChannel().sendMessage(builder.toString());
    }
}
