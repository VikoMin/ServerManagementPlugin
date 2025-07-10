package smp.commandSystem.commands.discord.admin;

import org.javacord.api.event.message.MessageCreateEvent;
import smp.commandSystem.discord.DiscordCommand;

public class ExitCommand extends DiscordCommand {
    public ExitCommand() {
        super("exit", " -> Stops the server.", 0,true, false);
    }

    @Override
    public void run(MessageCreateEvent listener) {
        Runtime.getRuntime().exit(0);
    }
}
