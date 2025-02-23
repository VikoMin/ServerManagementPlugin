package smp.models;

import static arc.util.Strings.canParseInt;

public class AchievementCondition {
    public int priotity;
    public String rankId;
    public String rankName;
    public String rankPrefix;
    public boolean adminPerms = false;
    public boolean consolePerms = false;

    public AchievementCondition(){
    }
    public AchievementCondition(String rankId, String rankName, String rankPrefix, int priority){
        this.rankId = rankId;
        this.rankName = rankName;
        this.rankPrefix = rankPrefix;
        this.priotity = priority;
    }
    public void set(String key, Object value){
        switch (key) {
            case "rankId": rankId = (String) value; return;
            case "rankName": rankName = (String) value; return;
            case "rankPrefix": rankPrefix = (String) value; return;
            case "priority": if (canParseInt((String) value)) priotity = Integer.parseInt((String) value);; return;
            case "adminPerms": adminPerms = (boolean) value; return;
            case "consolePerms": consolePerms = (boolean) value; return;
        }
    }
    public Object get(String key){
        return switch (key) {
            case "rankId" -> rankId;
            case "rankName" -> rankName;
            case "rankPrefix" -> rankPrefix;
            case "priority" -> priotity;
            case "adminPerms" -> adminPerms;
            case "consolePerms" -> consolePerms;
            default -> null;
        };
    }
}
