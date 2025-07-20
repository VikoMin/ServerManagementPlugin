package smp.commandSystem.commands.discord.console;

import org.javacord.api.event.message.MessageCreateEvent;
import smp.commandSystem.discord.DiscordCommand;
import smp.models.PlayerData;
import smp.models.Rank;

import static smp.database.DatabaseSystem.*;
import static smp.database.players.FindPlayerData.getPlayerDataAnyway;
import static smp.functions.Utilities.createHashMap;
import static smp.other.InitializeRanks.initializeRanks;

public class MagicStickCommand extends DiscordCommand {
    public MagicStickCommand() {
        super("wand", " <collection> <filter> <key> <value> -> Changes the value of document field in collection.", 4,false, true);
    }

    @Override
    public void run(MessageCreateEvent listener) {
        String[] args = this.params;
        switch (args[0]){
            case "players" -> {
                PlayerData data = getPlayerDataAnyway(args[1]);
                data.set(args[2], args[3]);
                updateDatabaseDocument(data, playerCollection, "_id", data.id);
            }
            case "ranks" -> {
                Rank data = findDatabaseDocument(rankCollection, createHashMap("id", args[1]));
                data.set(args[2], args[3]);
                updateDatabaseDocument(data, rankCollection, "id", data.id);
                initializeRanks();
            }
        }
        listener.getChannel().sendMessage("Field has been altered");
    }
}
