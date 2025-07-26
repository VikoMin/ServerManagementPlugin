package smp.commandSystem.commands.discord.basic;

import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import smp.commandSystem.discord.DiscordCommand;

public class PingCommand extends DiscordCommand {
    public PingCommand() {
        super("ping", "ping!", 1);
    }

    @Override
    public void run(MessageCreateEvent listener) {
        String[] args = this.params;
        User user = this.cmdUser;
        listener.getChannel().sendMessage(args[0]);
    }
}
