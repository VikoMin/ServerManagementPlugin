package smp.functions;

import mindustry.gen.Groups;
import mindustry.gen.Player;

public class FindPlayer {
    public static Player findPlayerByName(String name){
        return Groups.player.find(player -> player.plainName().toLowerCase().contains(name.toLowerCase()));
    }
}
