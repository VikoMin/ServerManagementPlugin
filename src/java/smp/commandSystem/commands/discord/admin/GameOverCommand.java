package smp.commandSystem.commands.discord.admin;

import arc.Events;
import mindustry.game.EventType;
import mindustry.game.Team;
import org.javacord.api.event.message.MessageCreateEvent;
import smp.commandSystem.discord.DiscordCommand;

import static java.lang.Integer.parseInt;

public class GameOverCommand extends DiscordCommand {
    public GameOverCommand() {
        super("gameover", " -> executes gameover.", 0,true, false);
    }

    @Override
    public void run(MessageCreateEvent listener) {
        Events.fire(new EventType.GameOverEvent(Team.derelict));
        listener.getChannel().sendMessage("Game over executed.");
    }
}
