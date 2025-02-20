package smp.commands.ds.discord.console;

import arc.net.Server;
import arc.util.CommandHandler;
import mindustry.Vars;
import mindustry.server.ServerControl;
import org.javacord.api.event.message.MessageCreateEvent;
import smp.commands.ds.discord.DiscordCommand;

import java.io.File;

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
