package smp.models;

import mindustry.content.Fx;
import mindustry.gen.Player;

import java.util.ArrayList;

import static arc.util.Strings.canParseInt;
import static smp.functions.Utilities.canParseLong;

public class Rank {
    private static Rank rank;
    public int priotity;
    public String rankId;
    public String rankName;
    public boolean adminPerms = false;
    public boolean consolePerms = false;

    public Rank(){
    }
    public Rank(String rankId, String rankName, int priority){
        this.rankId = rankId;
        this.rankName = rankName;
        this.priotity = priority;
    }
    public void changeValue(String key, Object value){
        switch (key) {
            case "rankId": rankId = (String) value;
            case "rankName": rankName = (String) value;
            case "priority": priotity = (int) value;
            case "adminPerms": adminPerms = (boolean) value;
            case "consolePerms": consolePerms = (boolean) value;
        }
    }
}
