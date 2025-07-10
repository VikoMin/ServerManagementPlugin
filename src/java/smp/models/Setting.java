package smp.models;

import arc.Core;

import java.net.Inet4Address;
import java.net.UnknownHostException;

public class Setting {
    public String discordURL = "none";
    public String ip = Inet4Address.getLocalHost().getHostAddress();
    public int port = Core.settings.getInt("port");
    public String serverName = Core.settings.getString("servername");
    public String serverDescription = Core.settings.getString("desc");
    public String type = "default";
    public String discordPrefix = "none";

    public Setting() throws UnknownHostException {
    }
    public void set(String key, Object value){

        switch (key) {
            case "discordURL": discordURL = (String) value; break;
            case "ip": ip = (String) value; break;
            case "port": port = (int) value; break;
            case "serverName": serverName = (String) value; break;
            case "serverDescription": serverDescription = (String) value; break;
            case "type": type = (String) value; break;
            case "discordPrefix": discordPrefix = (String) value;
        }
    }
    public Object get(String key){
        return switch (key) {
            case "discordURL" -> discordURL;
            case "ip" -> ip;
            case "port" -> port;
            case "serverName" -> serverName;
            case "serverDescription" -> serverDescription;
            case "type" -> type;
            case "discordPrefix" -> discordPrefix;
            default -> null;
        };
    }
}
