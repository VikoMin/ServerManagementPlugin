package smp.commands.ds.discord.console;

import org.javacord.api.event.message.MessageCreateEvent;
import smp.commands.ds.discord.DiscordCommand;
import smp.models.PlayerData;
import smp.models.Rank;
import smp.models.Setting;

import static arc.util.Strings.canParseInt;
import static java.lang.Integer.parseInt;
import static smp.database.players.FindPlayerData.getPlayerDataAnyway;
import static smp.database.players.PlayerFunctions.updateData;
import static smp.database.ranks.FindRank.findRank;
import static smp.database.ranks.FindRank.updateRank;
import static smp.database.settings.FindSetting.findSetting;
import static smp.database.settings.FindSetting.updateSetting;
import static smp.other.InitializeRanks.initializeRanks;

public class MagicStickCommand extends DiscordCommand {
    public MagicStickCommand() {
        super("wand", " <collection> <filter> <key> <value> -> Changes the value of document field in collection.", 2,false, true);
    }

    @Override
    public void run(MessageCreateEvent listener) {
        String[] args = this.params;
        switch (args[0]){
            case "players" -> {
                PlayerData data = getPlayerDataAnyway(args[1]);
                data.changeValue(args[2], args[3]);
                updateData(data);
            }
            case "settings" -> {
                if (!canParseInt(args[1])) {listener.getChannel().sendMessage("Filter should be integer!"); return;}
                Setting data = findSetting(Integer.parseInt(args[1]));
                data.changeValue(args[2], args[3]);
                updateSetting(data);
            }
            case "ranks" -> {
                Rank data = findRank(args[1]);
                data.changeValue(args[2], args[3]);
                updateRank(data);
                initializeRanks();
            }
        }
        listener.getChannel().sendMessage("Field has been altered");
    }
}
