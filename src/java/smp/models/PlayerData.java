package smp.models;

import mindustry.content.Fx;
import mindustry.gen.Player;

import java.util.ArrayList;

import static arc.util.Strings.canParseInt;
import static smp.functions.Utilities.canParseLong;

public class PlayerData {
    public String uuid;
    public int id;
    public String name = "<none>";
    public String rawName = "<none>";
    public String rank = "player";
    public String joinMessage = "@ joined!";
    public String ip = "<none>";
    public ArrayList<Punishment> punishments = new ArrayList<>();
    public long discordId = 0;
    public ArrayList<String> achievements = new ArrayList<>();
    public int playtime = 0;
    public boolean isVip = false;
    public String customPrefix = "<none>";
    public String effectType = "default";
    public int effectId = Fx.fire.id;
    public boolean effectIsAlive = true;
    public PlayerData(){
    }
    public PlayerData(String uuid, int id){
        this.uuid = uuid;
        this.id = id;
    }
    public void set(String key, Object value, Player player){
        switch (key) {
            case "uuid": uuid = (String) value; return;
            case "name": name = (String) value; return;
            case "rawname": rawName = (String) value; return;
            case "rank": rank = (String) value; return;
            case "joinMessage": joinMessage = (String) value; return;
            case "ip": ip = (String) value; return;
            case "punishments": punishments = (ArrayList<Punishment>) value; return;
            case "discordId":  if(canParseLong((String) value)) discordId = Long.parseLong((String) value); return;
            case "playtime": if (canParseInt((String) value)) id = Integer.parseInt((String) value); return;
            case "customPrefix": customPrefix = (String) value; return;
            default: player.sendMessage("[red]No such key!");
        }
    }

    public void set(String key, Object value){
        switch (key) {
            case "uuid": uuid = (String) value; return;
            case "name": name = (String) value; return;
            case "rawname": rawName = (String) value; return;
            case "rank": rank = (String) value; return;
            case "joinMessage": joinMessage = (String) value; return;
            case "ip": ip = (String) value; return;
            case "punishments": punishments = (ArrayList<Punishment>) value; return;
            case "discordId":  if(canParseLong((String) value)) discordId = Long.parseLong((String) value); return;
            case "playtime": if (canParseInt((String) value)) id = Integer.parseInt((String) value); return;
            case "customPrefix": customPrefix = (String) value;
        }
    }

    public Object get(String key){
        return switch (key) {
            case "uuid" -> uuid;
            case "name" -> name;
            case "rawname" -> rawName;
            case "rank" -> rank;
            case "joinMessage" -> joinMessage;
            case "ip" -> ip;
            case "punishments" -> punishments;
            case "discordId" -> discordId;
            case "playtime" -> playtime;
            case "customPrefix" -> customPrefix;
            default -> null;
        };
    }
}
