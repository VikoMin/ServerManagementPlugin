package smp.functions;

import mindustry.gen.Player;
import mindustry.net.NetConnection;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import smp.models.PlayerData;

import java.util.Date;
import java.util.List;

import static smp.Variables.*;
import static smp.database.players.FindPlayerData.getPlayerDataAnyway;
import static smp.database.players.FindPlayerData.getPlayerDataByIP;
import static smp.functions.Utilities.notNullElse;
import static smp.functions.Wrappers.timeToDuration;

public class Checks {
    public static boolean isModerator(MessageCreateEvent listener){
        User user = listener.getMessageAuthor().asUser().get();
        Server server = listener.getServer().get();
        Role moderatorRole = server.getRoleById(mindustryModeratorID).get();
        List<Role> roles = user.getRoles(server);
        if (roles.contains(moderatorRole)){
            return true;
        } else {
            return false;
        }
    }
    public static boolean isConsole(MessageCreateEvent listener){
        User user = listener.getMessageAuthor().asUser().get();
        Server server = listener.getServer().get();
        Role moderatorRole = server.getRoleById(mindustryConsoleID).get();
        List<Role> roles = user.getRoles(server);
        if (roles.contains(moderatorRole)){
            return true;
        } else {
            return false;
        }
    }
    public static void kickIfBanned(NetConnection player){
        PlayerData data = getPlayerDataByIP(player.address);
        if (data == null){
            return;
        }
        long lastBan = data.lastBan;
        Date date = new Date();
        if (lastBan > date.getTime()) {
            String timeUntilUnban = timeToDuration(lastBan);
            player.kick("[red]You have been banned!\n\n" +"[white]Duration: " + timeUntilUnban + " until unban\n\nIf you think this is a mistake, make sure to appeal ban in our discord: " + discordURL, 0);
        }
    }
    public static void kickIfBanned(Player player){
        PlayerData data = notNullElse(getPlayerDataAnyway(player.uuid()), getPlayerDataAnyway(player.ip()));
        if (data == null){
            return;
        }
        long lastBan = data.lastBan;
        Date date = new Date();
        if (lastBan > date.getTime()) {
            String timeUntilUnban = timeToDuration(lastBan);
            player.kick("[red]You have been banned!\n\n" +"[white]Duration: " + timeUntilUnban + " until unban\n\nIf you think this is a mistake, make sure to appeal ban in our discord: " + discordURL, 0);
        }
    }
}
