package smp.functions;

import arc.Core;
import mindustry.gen.Call;
import mindustry.gen.Player;
import org.javacord.api.entity.user.User;
import smp.models.PlayerData;
import smp.models.Punishment;

import java.util.Date;
import java.util.Objects;
import java.util.Scanner;

import static java.lang.Long.parseLong;
import static smp.Variables.discordURL;
import static smp.database.players.PlayerFunctions.updateData;
import static smp.discord.Bot.messageLogChannel;
import static smp.discord.embeds.BanEmbed.banEmbed;
import static smp.functions.FindPlayer.findPlayerByName;
import static smp.functions.Wrappers.timeToDuration;

public class Utilities {
    public static <T> T notNullElse(T value, T value2){
        return value != null ? value : value2;
    }
    public static boolean canParseLong(String s){
        return parseLong(s) != Long.MIN_VALUE;
    }
    public static void banPlayer(Date date, String reason, PlayerData data, Player moderator){
        data.changeValue("lastBan", String.valueOf(date.getTime()));
        Player plr = findPlayerByName(data.name);
        if (plr != null){
            Call.sendMessage(plr.plainName() + " has been banned for: " + reason);
            plr.con.kick("[red]You have been banned!\n\n" + "[white]Reason: " + reason +"\nDuration: " + timeToDuration(date.getTime()) + " until unban\nIf you think this is a mistake, make sure to appeal ban in our discord: " + discordURL, 0);
        }
        updateData(data);
        messageLogChannel.sendMessage(banEmbed(data, reason, date.getTime(), moderator.plainName()));
    }

    public static void banPlayer(Date date, String reason, PlayerData data, User moderator){
        Punishment punishment = new Punishment("ban", date.getTime(), reason, moderator.getName());
        data.punishments.add(punishment);
        Player plr = findPlayerByName(data.name);
        if (plr != null){
            Call.sendMessage(plr.plainName() + " has been banned for: " + reason);
            plr.con.kick("[red]You have been banned!\n\n" + "[white]Reason: " + reason +"\nDuration: " + timeToDuration(date.getTime()) + " until unban\nIf you think this is a mistake, make sure to appeal ban in our discord: " + discordURL, 0);
        }
        updateData(data);
        messageLogChannel.sendMessage(banEmbed(data, reason, date.getTime(), moderator.getName()));
    }

    public static void unbanPlayer(PlayerData data){
        data.changeValue("lastBan", "0");
        updateData(data);
    }

    public static void makeCoreSettingString(String settingInputText, String settingName){
        if (Objects.equals(Core.settings.getString(settingName), "none") || Core.settings.getString(settingName) == null) {
            Scanner inp = new Scanner(System.in);
            System.out.println(settingInputText);
            String settingValue = inp.next();
            Core.settings.put(settingName, settingValue);
            inp.close();
        }
    }

    public static void makeCoreSettingLong(String settingInputText, String settingName){
        if (Objects.equals(Core.settings.getLong(settingName), 0L) || Core.settings.getLong(settingName) == null) {
            Scanner inp = new Scanner(System.in);
            System.out.println(settingInputText);
            Long settingValue = inp.nextLong();
            Core.settings.put(settingName, settingValue);
            inp.close();
        }
    }
}
