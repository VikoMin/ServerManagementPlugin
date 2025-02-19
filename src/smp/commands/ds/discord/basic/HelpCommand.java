package smp.commands.ds.discord.basic;

import org.javacord.api.event.message.MessageCreateEvent;
import smp.commands.ds.discord.DiscordCommand;

import static smp.commands.CommandRegister.discordCommands;

public class HelpCommand extends DiscordCommand {
    public HelpCommand() {
        super("help", " -> Shows the list of commands", 0);
    }

    @Override
    public void run(MessageCreateEvent listener) {
        StringBuilder builder = new StringBuilder();
        builder.append("```\n");
        for (DiscordCommand cmd : discordCommands){
            if (!cmd.isConsole) {
                builder.append(cmd.name).append(cmd.desc).append("\n");
            }
        }
        builder.append("```");
        listener.getChannel().sendMessage(builder.toString());
    }
}
