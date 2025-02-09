package smp.commands.discord;

import mindustry.gen.Groups;
import org.javacord.api.event.message.MessageCreateEvent;
import smp.database.FindPlayerData;
import smp.models.PlayerData;

import java.io.IOException;

import static smp.render.Render.renderMap;

public class RenderCommand extends DiscordCommand {
    public RenderCommand() {
        super("render", " -> Renders map to show it to players", 0);
    }

    @Override
    public void run(MessageCreateEvent listener) {
        try {
            listener.getChannel().sendMessage(renderMap());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
