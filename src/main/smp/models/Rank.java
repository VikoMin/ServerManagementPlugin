package smp.models;

import static arc.util.Strings.canParseInt;
import static arc.util.Strings.parseInt;

public class Rank {
    public int priotity;
    public String id;
    public String name;
    public String prefix;
    public boolean admin = false;
    public boolean console = false;

    public Rank(){
    }

    public Rank(String id, String name, String prefix, int priority){
        this.id = id;
        this.name = name;
        this.prefix = prefix;
        this.priotity = priority;
    }

    public void set(String key, Object value){
        switch (key) {
            case "id": id = (String) value; return;
            case "name": name = (String) value; return;
            case "prefix": prefix = (String) value; return;
            case "priority": if (canParseInt((String) value)) priotity = Integer.parseInt((String) value);; return;
            case "admin": admin = (boolean) value; return;
            case "console": console = (boolean) value; return;
        }
    }

    public Object get(String key){
        return switch (key) {
            case "id" -> id;
            case "name" -> name;
            case "prefix" -> prefix;
            case "priority" -> priotity;
            case "admin" -> admin;
            case "console" -> console;
            default -> null;
        };
    }
}
