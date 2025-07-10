package smp.discord;

import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import smp.models.PlayerData;
import smp.models.Rank;

import java.util.List;

import static smp.Variables.mindustryConsoleID;
import static smp.Variables.mindustryModeratorID;
import static smp.database.players.FindPlayerData.getPlayerDataByDiscordID;
import static smp.database.ranks.FindRank.findRank;

public class DiscordChecks {
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
    public static boolean isConsole(MessageCreateEvent listener) {
        User user = listener.getMessageAuthor().asUser().get();
        Server server = listener.getServer().get();
        Role moderatorRole = server.getRoleById(mindustryConsoleID).get();
        List<Role> roles = user.getRoles(server);
        if (roles.contains(moderatorRole)) {
            return true;
        } else {
            return false;
        }
    }
    public static boolean hasRank(MessageCreateEvent listener, PlayerData receiver, Rank rank){
        PlayerData sender = getPlayerDataByDiscordID(listener.getMessageAuthor().getId());
        System.out.println(listener.getMessageAuthor().getId());
        if (sender == null) { listener.getChannel().sendMessage("Your account is not linked!"); return false;}
        if (findRank(sender.rank).priotity > findRank(receiver.rank).priotity)
        { listener.getChannel().sendMessage("You can't change rank of person that ranked higher than yoou!"); return false;}
        if (findRank(sender.rank).priotity > rank.priotity)
        { listener.getChannel().sendMessage("You can't change rank of that higher than your own!"); return false;}
        return true;
    }
}
