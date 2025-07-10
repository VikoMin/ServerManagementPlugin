package smp.functions;

import arc.Core;
import arc.Events;
import arc.struct.Seq;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.game.Team;
import mindustry.gen.Call;
import mindustry.gen.Player;
import mindustry.maps.Map;
import mindustry.server.ServerControl;
import org.javacord.api.entity.user.User;
import smp.models.PlayerData;
import smp.models.Punishment;

import java.util.*;

import static java.lang.Long.parseLong;
import static java.lang.Runtime.getRuntime;
import static smp.Variables.*;
import static smp.database.players.PlayerFunctions.updateData;
import static smp.database.punishments.FindPunishment.findPunishmentLastBan;
import static smp.discord.Bot.messageLogChannel;
import static smp.discord.embedSystem.embeds.BanEmbed.banEmbed;
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
        Punishment punishment = new Punishment("ban", date.getTime(), reason, moderator.plainName());
        data.punishments.add(punishment);
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
        Punishment punishment = findPunishmentLastBan(data);
        data.punishments.remove(punishment);
        punishment.punishmentType = "unbanned";
        data.punishments.add(punishment);
        updateData(data);
    }

    public static void warnPlayer(Date date, String reason, PlayerData data, User moderator){
        Punishment punishment = new Punishment("warn", date.getTime(), reason, moderator.getName());
        data.punishments.add(punishment);
        updateData(data);
    }

    public static void makeCoreSettingString(String settingInputText, String settingName){
        if (Objects.equals(Core.settings.getString(settingName), "none") || Core.settings.getString(settingName) == null) {
            Scanner inp = new Scanner(System.in);
            System.out.println(settingInputText);
            String settingValue = inp.next();
            Core.settings.put(settingName, settingValue);
        }
    }

    public static void makeCoreSettingLong(String settingInputText, String settingName){
        if (Objects.equals(Core.settings.getLong(settingName), 0L) || Core.settings.getLong(settingName) == null) {
            Scanner inp = new Scanner(System.in);
            System.out.println(settingInputText);
            Long settingValue = inp.nextLong();
            Core.settings.put(settingName, settingValue);
        }
    }

    public static String joinArrayString(String[] array, int startFrom){
        StringBuilder string = new StringBuilder();
        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(array));
        arrayList.remove(arrayList.size()-1);
        for (String str : arrayList){
            if (arrayList.indexOf(str) >= startFrom) {
                if (arrayList.indexOf(str) != arrayList.size()-1) {
                    string.append(str).append(" ");
                } else {
                    string.append(str);
                }
            }
        }
        return string.toString();
    }

    public static void voteCanceled(){
        Call.sendMessage("[red]Vote has been canceled!");
        votes.set(0);
        votedPlayer.clear();
    }

    public static void voteSuccess(Map map){
        Call.sendMessage("[green]Vote success! Changing map!");
        ServerControl.instance.setNextMap(map);
        Events.fire(new EventType.GameOverEvent(Team.derelict));
        votes.set(0);
        votedPlayer.clear();
    }

    public static Seq<Map> getMaps(){
        Seq<Map> maps = new Seq<>();
        for(Map map : Vars.maps.customMaps()){
            maps.add(map);
        }
        return maps;
    }

    public static void exit(){
        Core.app.exit();
        getRuntime().exit(0);
    }
}
