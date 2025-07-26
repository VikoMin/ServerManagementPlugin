package smp.commandSystem.commands.discord.admin;

import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import smp.commandSystem.discord.DiscordCommand;
import smp.database.players.FindPlayerData;
import smp.models.PlayerData;
import smp.models.Punishment;

import java.util.Date;

import static arc.util.Strings.canParseInt;
import static java.lang.Integer.parseInt;
import static smp.database.DatabaseSystem.updateDatabaseDocument;
import static smp.database.DatabaseSystem.playerCollection;
import static smp.functions.Wrappers.formatBanTime;

public class WarnCommand extends DiscordCommand {
    public WarnCommand() {
        super("warn", " <id/name/uuid> -> Unbans the player.", 3,true, false);
    }

    @Override
    public void run(MessageCreateEvent listener) {
        String[] args = this.params;
        PlayerData plr;
        Date time = formatBanTime(args[1]);
        String reason = args[2];

        if (canParseInt(args[0])) {
            plr = FindPlayerData.getPlayerData(parseInt(args[0]));
        } else {
            plr = FindPlayerData.getPlayerData(args[0]);
        }

        if (plr == null) {listener.getChannel().sendMessage("Could not find that player!"); return;}

        if (time == null) {listener.getChannel().sendMessage("Incorrect time!"); return;}

        warnPlayer(time, reason, plr, listener.getMessageAuthor().asUser().get());
        listener.getChannel().sendMessage("Warned: " + plr.name);
    }

    public static void warnPlayer(Date date, String reason, PlayerData data, User moderator){
        Punishment punishment = new Punishment("warn", date.getTime(), reason, moderator.getName());
        data.punishments.add(punishment);
        updateDatabaseDocument(data, playerCollection, "_id", data.id);
    }
}
