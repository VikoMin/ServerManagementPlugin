package smp.models;

import arc.Core;
import arc.Settings;
import mindustry.Vars;

import java.net.Inet4Address;
import java.net.UnknownHostException;

public class Setting {
    private static Setting setting;
    public String discordURL = "none";
    public String ip = Inet4Address.getLocalHost().getHostAddress();
    public int port = Core.settings.getInt("port");
    public String serverName = Core.settings.getString("name");
    public String serverDescription = Core.settings.getString("desc");
    public String type = "default";
    public String discordPrefix = "none";

    public Setting() throws UnknownHostException {
    }
    public void changeValue(String key, Object value){
        switch (key) {
            case "discordURL": discordURL = (String) value;
            case "ip": ip = (String) value;
            case "port": port = (int) value;
            case "serverName": serverName = (String) value;
            case "serverDescription": serverDescription = (String) value;
            case "type": type = (String) value;
            case "discordPrefix": type = (String) discordPrefix;
        }
    }
}
