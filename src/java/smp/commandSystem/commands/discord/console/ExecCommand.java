package smp.commandSystem.commands.discord.console;

import mindustry.server.ServerControl;
import org.javacord.api.event.message.MessageCreateEvent;
import smp.commandSystem.discord.DiscordCommand;

public class ExecCommand extends DiscordCommand {
    public ExecCommand() {
        super("exec", " <command> -> Executes server command.", 1,false, true);
    }

    @Override
    public void run(MessageCreateEvent listener) {
        String[] args = this.params;
        ServerControl.instance.handleCommandString(args[0]);
    }
}
