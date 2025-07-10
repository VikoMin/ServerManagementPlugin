package smp.commandSystem.commands.discord.console;

import com.mongodb.client.MongoCursor;
import org.javacord.api.event.message.MessageCreateEvent;
import smp.commandSystem.discord.DiscordCommand;
import smp.models.PlayerData;
import smp.models.Rank;
import smp.models.Setting;

import static smp.database.DatabaseSystem.*;
import static smp.database.players.PlayerFunctions.updateData;

public class ResetColumnCommand extends DiscordCommand {
    public ResetColumnCommand() {
        super("resetcolumn", " <collection> <columnFrom> -> Resets the column of collection in all documents", 2,false, true);
    }

    @Override
    public void run(MessageCreateEvent listener) {
        String[] args = this.params;
        switch (args[0]){
            case "players" -> {
                try (MongoCursor<PlayerData> cursor = playerCollection.find().iterator()) {
                    while (cursor.hasNext()) {
                        PlayerData data = new PlayerData();
                        PlayerData newData = cursor.next();
                        newData.set(args[1], data.get(args[1]));
                        updateData(newData);
                    }
                }
            }
            case "settings" -> {
                try (MongoCursor<Setting> cursor = settingCollection.find().iterator()) {
                    while (cursor.hasNext()) {

                    }
                }
            }
            case "ranks" -> {
                try (MongoCursor<Rank> cursor = rankCollection.find().iterator()) {
                    while (cursor.hasNext()) {

                    }
                }
            }
        }
        listener.getChannel().sendMessage("Documents updated, column has been reset");
    }
}
