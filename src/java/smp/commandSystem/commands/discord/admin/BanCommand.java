package smp.commandSystem.commands.discord.admin;

import mindustry.gen.Call;
import mindustry.gen.Player;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import smp.commandSystem.discord.DiscordCommand;
import smp.database.players.FindPlayerData;
import smp.models.PlayerData;
import smp.models.Punishment;

import java.util.Date;

import static arc.util.Strings.canParseInt;
import static java.lang.Integer.parseInt;
import static smp.vars.Variables.discordURL;
import static smp.database.DatabaseSystem.updateDatabaseDocument;
import static smp.database.DatabaseSystem.playerCollection;
import static smp.discord.Bot.messageLogChannel;
import static smp.discord.embedSystem.embeds.BanEmbed.banEmbed;
import static smp.functions.FindPlayer.findPlayerByName;
import static smp.functions.Wrappers.formatBanTime;
import static smp.functions.Wrappers.timeToDuration;

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

        banPlayer(time, reason, plr, listener.getMessageAuthor().asUser().get());
        listener.getChannel().sendMessage("Banned: " + plr.name);
    }

    public static void banPlayer(Date date, String reason, PlayerData data, User moderator){
        Punishment punishment = new Punishment("ban", date.getTime(), reason, moderator.getName());
        data.punishments.add(punishment);
        Player plr = findPlayerByName(data.name);
        if (plr != null){
            Call.sendMessage(plr.plainName() + " has been banned for: " + reason);
            plr.con.kick("[red]You have been banned!\n\n" + "[white]Reason: " + reason +"\nDuration: " + timeToDuration(date.getTime()) + " until unban\nIf you think this is a mistake, make sure to appeal ban in our discord: " + discordURL, 0);
        }
        updateDatabaseDocument(data, playerCollection, "_id", data.id);
        messageLogChannel.sendMessage(banEmbed(data, reason, date.getTime(), moderator.getName()));
    }
}
