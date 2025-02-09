package smp.models;

import mindustry.content.Blocks;
import mindustry.content.Fx;
import mindustry.gen.Call;
import mindustry.gen.Player;

import java.util.ArrayList;
import java.util.Objects;

import static arc.util.Strings.canParseInt;
import static smp.functions.Utilities.canParseLong;

public class PlayerData {
    private static PlayerData playerData;
    public String uuid;
    public int id;
    public String name = "<none>";
    public String rawName = "<none>";
    public String rank = "player";
    public String joinMessage = "@ joined!";
    public String ip = "<none>";
    public long lastBan = 0;
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
    public void changeValue(String key, String value, Player player){
        switch (key) {
            case "uuid": uuid = value; return;
            case "name": name = value; return;
            case "rawname": rawName = value; return;
            case "rank": rank = value; return;
            case "joinMessage": joinMessage = value; return;
            case "ip": ip = value; return;
            case "lastBan": if(canParseLong(value)) lastBan = Long.parseLong(value); else player.sendMessage("[red]Incorrect value!"); return;
            case "discordId":  if(canParseLong(value)) discordId = Long.parseLong(value); else player.sendMessage("[red]Incorrect value!"); return;
            case "playtime": if (canParseInt(value)) id = Integer.parseInt(value); else player.sendMessage("[red]Incorrect value!"); return;
            case "customPrefix": customPrefix = value; return;
            default: player.sendMessage("[red]No such key!");
        }
    }

    public void changeValue(String key, String value){
        switch (key) {
            case "uuid": uuid = value; return;
            case "name": name = value; return;
            case "rawname": rawName = value; return;
            case "rank": rank = value; return;
            case "joinMessage": joinMessage = value; return;
            case "ip": ip = value; return;
            case "lastBan": if(canParseLong(value)) lastBan = Long.parseLong(value); return;
            case "discordId":  if(canParseLong(value)) discordId = Long.parseLong(value); return;
            case "playtime": if (canParseInt(value)) id = Integer.parseInt(value); return;
            case "customPrefix": customPrefix = value;
        }
    }
}
