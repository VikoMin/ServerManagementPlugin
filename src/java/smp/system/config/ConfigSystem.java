package smp.system.config;

import arc.util.Log;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import static smp.vars.Variables.*;

public class ConfigSystem {
    public static HashMap<String, Boolean> booleans = new HashMap<>();
    public static HashMap<String, Integer> integers = new HashMap<>();
    public static HashMap<String, Long> longs = new HashMap<>();
    public static HashMap<String, String> strings = new HashMap<>();

    public static void registerConfig(String path, String fileName){
        try {
            JSONObject object = (JSONObject) new JSONParser().parse(new FileReader(path + "/" + fileName));
            registerField(object, "");
        }
        catch (FileNotFoundException e){
            Log.err("Could not find config!");
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static void registerField(JSONObject object, String name){
        for (Object key : object.keySet()){
            if (object.get(key) instanceof JSONObject){
                registerField((JSONObject) object.get(key), name + key + ".");
            } else {
                Log.info("Config field loaded: " + name + key);
                Object value = object.get(key);
                if (value instanceof Boolean) booleans.put(name + key, (Boolean) value);
                if (value instanceof String) strings.put(name + key, (String) value);
                if (value instanceof Long) longs.put(name + key, (Long) value);
                if (value instanceof Integer) integers.put(name + key, (Integer) value);
            }
        }
    }

    public static <T> T getConfigField(String name, HashMap<String, T> map){
        return map.get(name);
    }

    public static void updateVariables(){
        // DISCORD
        discordEnabled = getConfigField("discord.enabled", booleans);

        if (discordEnabled) {
            mindustryModeratorID = getConfigField("discord.configuration.mindustry-moderator-id", longs);
            mindustryConsoleID = getConfigField("discord.configuration.mindustry-console-id", longs);
            messageLogChannelID = getConfigField("discord.log.message-channel-id", longs);
            messageBanLogChannelID = getConfigField("discord.log.ban-channel-id", longs);
            discordURL = getConfigField("discord.configuration.invite-url", strings);
            discordToken = getConfigField("discord.configuration.token", strings);
            discordPrefix = getConfigField("discord.configuration.prefix", strings);
            allDiscordCommandsEnabled = getConfigField("discord.commands.all-commands", booleans);
        }

        // Mindustry

        ranksEnabled = getConfigField("mindustry.ranks.enabled", booleans);
        betterBansEnabled = getConfigField("mindustry.better-bans", booleans);
        griefingWarningsEnabled = getConfigField("mindustry.griefing-warnings", booleans);
        timeCounterEnabled = getConfigField("mindustry.time-counter", booleans);
        allCommandsEnabled = getConfigField("mindustry.commands.all-commands", booleans);

        welcomeMenuEnabled = getConfigField("mindustry.menus.welcome-menu", booleans);
        welcomeMenuText = getConfigField("mindustry.menus.welcome-menu-text", strings);
        welcomeMenuTitle = getConfigField("mindustry.menus.welcome-menu-title", strings);

        // Global

        type = getConfigField("global.configuration.type", strings);
        mongoDbIP = getConfigField("global.configuration.mongodb-ip", strings);
        loc = getConfigField("global.configuration.loc", strings);
        playerBasedLoc = getConfigField("global.configuration.playerbased-loc", booleans);

    }
}
