package smp.commands.ds.discord.basic;

import org.javacord.api.event.message.MessageCreateEvent;
import smp.commands.ds.discord.DiscordCommand;

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
