package smp.models;

import mindustry.content.Fx;
import mindustry.gen.Player;

import java.util.ArrayList;

import static arc.util.Strings.canParseInt;
import static arc.util.Strings.parseInt;
import static smp.functions.Utilities.canParseLong;

public class Rank {
    public int priotity;
    public String rankId;
    public String rankName;
    public String rankPrefix;
    public boolean adminPerms = false;
    public boolean consolePerms = false;

    public Rank(){
    }
    public Rank(String rankId, String rankName, String rankPrefix, int priority){
        this.rankId = rankId;
        this.rankName = rankName;
        this.rankPrefix = rankPrefix;
        this.priotity = priority;
    }
    public void changeValue(String key, Object value){
        switch (key) {
            case "rankId": rankId = (String) value; return;
            case "rankName": rankName = (String) value; return;
            case "rankPrefix": rankPrefix = (String) value; return;
            case "priority": if (canParseInt((String) value)) priotity = Integer.parseInt((String) value);; return;
            case "adminPerms": adminPerms = (boolean) value; return;
            case "consolePerms": consolePerms = (boolean) value; return;
        }
    }
}
