package smp.commands.discord;

import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import mindustry.gen.Groups;
import mindustry.gen.Player;
import org.javacord.api.event.message.MessageCreateEvent;
import smp.models.PlayerData;

import java.util.regex.Pattern;

import static mindustry.Vars.netServer;
import static smp.database.InitializeDatabase.collection;

public class SearchCommand extends DiscordCommand {
    public SearchCommand() {
        super("search", " <name> -> Tries to find all players with familiar name .", 1,false, false);
    }

    @Override
    public void run(MessageCreateEvent listener) {
        String[] args = this.params;
        String name = args[0];
        StringBuilder list = new StringBuilder();
        Pattern pattern = Pattern.compile(".?" +name + ".?", Pattern.CASE_INSENSITIVE);
        list.append("```Results:\n\n");
        try (MongoCursor<PlayerData> cursor = collection.find(Filters.regex("name", pattern)).limit(25).iterator()) {
            while (cursor.hasNext()) {
                PlayerData csr = cursor.next();
                list.append(csr.name).append("; ID: ").append(csr.id).append("\n");
            }
        }
        list.append("```");
        listener.getChannel().sendMessage(String.valueOf(list));
    }
}
