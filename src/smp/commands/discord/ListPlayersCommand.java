package smp.commands.discord;

import mindustry.gen.Groups;
import org.javacord.api.event.message.MessageCreateEvent;
import smp.database.players.FindPlayerData;
import smp.models.PlayerData;

public class ListPlayersCommand extends DiscordCommand {
    public ListPlayersCommand() {
        super("list", " -> Shows current players on server", 0);
    }

    @Override
    public void run(MessageCreateEvent listener) {
        StringBuilder builder = new StringBuilder();
        builder.append("```\nPlayers online: " + Groups.player.size() + "\n");

        Groups.player.forEach(
                p -> {
                    PlayerData data = FindPlayerData.getPlayerData(p.uuid());
                    if (p.admin) {
                        builder.append("# [A] ").append(p.plainName());
                        builder.append("; ID: ").append(data.id).append("\n");
                    } else {
                        builder.append("# ").append(p.plainName());
                        builder.append("; ID: ").append(data.id).append("\n");
                    }
                }
        );
        builder.append("```");
        listener.getChannel().sendMessage(builder.toString());
    }
}
