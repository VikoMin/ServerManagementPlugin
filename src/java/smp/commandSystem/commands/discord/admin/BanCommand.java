package smp.commandSystem.commands.discord.admin;

import org.javacord.api.event.message.MessageCreateEvent;
import smp.commandSystem.discord.DiscordCommand;
import smp.database.players.FindPlayerData;
import smp.functions.Utilities;
import smp.models.PlayerData;

import java.util.Date;

import static arc.util.Strings.canParseInt;
import static java.lang.Integer.parseInt;
import static smp.database.players.PlayerFunctions.updateData;
import static smp.functions.Wrappers.formatBanTime;

public class BanCommand extends DiscordCommand {
    public BanCommand() {
        super("ban", " <id/name/uuid> <time> <reason> -> Bans the player.", 3,true, false);
    }

    @Override
    public void run(MessageCreateEvent listener) {
        String[] args = this.params;
        PlayerData plr = null;
        Date time = formatBanTime(args[1]);
        String reason = args[2];

        if (canParseInt(args[0])) {
            plr = FindPlayerData.getPlayerData(parseInt(args[0]));
        } else {
            plr = FindPlayerData.getPlayerData(args[0]);
        }

        if (plr == null) {listener.getChannel().sendMessage("Could not find that player!"); return;}

        if (time == null) {listener.getChannel().sendMessage("Incorrect time!"); return;}

        Utilities.banPlayer(time, reason, plr, listener.getMessageAuthor().asUser().get());
        listener.getChannel().sendMessage("Banned: " + plr.name);
    }
}
