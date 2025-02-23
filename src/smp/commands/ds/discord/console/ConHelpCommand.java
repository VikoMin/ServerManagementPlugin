package smp.commands.ds.discord.console;

import org.javacord.api.event.message.MessageCreateEvent;
import smp.commands.ds.discord.DiscordCommand;

import static smp.commands.CommandRegister.discordCommands;

public class ConHelpCommand extends DiscordCommand {
    public ConHelpCommand() {
        super("conhelp", " -> Shows the list of console commands", 0, false, true);
    }

    @Override
    public void run(MessageCreateEvent listener) {
        StringBuilder builder = new StringBuilder();
        builder.append("```\n");
        for (DiscordCommand cmd : discordCommands){
            if (cmd.isConsole) {
                builder.append(cmd.name).append(cmd.desc).append("\n");
            }
        }
        builder.append("```");
        listener.getChannel().sendMessage(builder.toString());
    }
}
