package smp.other;

import arc.Core;
import smp.Variables;
import smp.discord.DiscordCommandHandler;
import smp.models.Setting;

import java.net.UnknownHostException;
import java.util.Objects;
import java.util.Scanner;

import static smp.database.DatabaseSystem.settingCollection;
import static smp.database.settings.FindSetting.*;
import static smp.Variables.*;

public class InitializeSettings {

    public static void initializeSettings() throws UnknownHostException {
        Setting setting = findSettingOrCreate();
        if (Objects.equals(setting.discordPrefix, "none")){
            Scanner scanner = new Scanner(System.in);

            System.out.println("Looks like you don't have your prefix set up! Please, type the prefix for discord bot" +
                    "(ex: sbx. Each command will have to start with sbx):\n");

            setting.set("discordPrefix", scanner.next());
        }

        if (setting.discordURL == "none"){
            Scanner scanner = new Scanner(System.in);

            System.out.println("Looks like you don't have your discord server URL set up! Please, type the discord server URL for people to join!" +
                    ":\n");

            setting.set("discordURL", scanner.next());
        }

        DiscordCommandHandler.prefix = setting.discordPrefix;
        Variables.discordURL = setting.discordURL;
        setting.set("serverName", Core.settings.getString("servername"));
        setting.set("serverDescription", Core.settings.getString("desc"));
        if (findSetting(setting.port) == null){
            settingCollection.insertOne(setting);
        }
        updateSetting(setting);
        currentSetting = setting;
        System.out.println("Configuration has been checked, if you willing to perform any changes - make sure to use 'settingchange' command!");
    }
}
