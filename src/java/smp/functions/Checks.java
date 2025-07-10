package smp.functions;

import mindustry.gen.Player;
import mindustry.net.NetConnection;
import smp.models.PlayerData;
import smp.models.Punishment;

import java.util.Date;

import static smp.Variables.*;
import static smp.database.players.FindPlayerData.getPlayerDataByIP;
import static smp.database.punishments.FindPunishment.findPunishmentLastBan;
import static smp.functions.Wrappers.timeToDuration;

public class Checks {
    public static void kickIfBanned(NetConnection player){
        PlayerData data = getPlayerDataByIP(player.address);
        if (data == null){
            return;
        }
        Punishment lastBan = findPunishmentLastBan(data);
        if (lastBan == null) return;
        Date date = new Date();
        if (lastBan.punishmentDuration > date.getTime()) {
            String timeUntilUnban = timeToDuration(lastBan.punishmentDuration);
            player.kick("[red]You have been banned!\n\n" +
                    "[white]Reason: " + lastBan.punishmentReason + "\n\n"
                    +"[white]Moderator: " + lastBan.punishmentModerator + "\n\n"
                    +"[white]Duration: " + timeUntilUnban + " until unban" +
                    "\n\nIf you think this is a mistake, make sure to appeal ban in our discord: " + discordURL, 0);
        }
    }
    public static void kickIfBanned(Player player){
        PlayerData data = getPlayerDataByIP(player.con.address);
        if (data == null){
            return;
        }
        Punishment lastBan = findPunishmentLastBan(data);
        if (lastBan == null) return;
        Date date = new Date();
        if (lastBan.punishmentDuration > date.getTime()) {
            String timeUntilUnban = timeToDuration(lastBan.punishmentDuration);
            player.kick("[red]You have been banned!\n\n" +
                    "[white]Reason: " + lastBan.punishmentReason + "\n\n"
                    +"[white]Moderator: " + lastBan.punishmentModerator + "\n\n"
                    +"[white]Duration: " + timeUntilUnban + " until unban" +
                    "\n\nIf you think this is a mistake, make sure to appeal ban in our discord: " + discordURL, 0);
        }
    }
}
