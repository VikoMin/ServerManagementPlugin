package smp.history;

import mindustry.game.Team;
import mindustry.world.Block;
import mindustry.world.Tile;

import javax.xml.crypto.Data;
import java.util.Date;

public class HistoryObject {
    public final Tile tile;
    public final Block actionBlock;
    public final String uuid;
    public final String rawName;
    public final String action;
    public boolean parentObject;
    public int rotation;
    public Team team;
    public final Date time;
    public Object conf;

    public HistoryObject(Tile tile, Block actionBlock, String rawName, String uuid, String action, int rotation, Team team, Date time, Object config, boolean parentObject) {
        this.tile = tile;
        this.actionBlock = actionBlock;
        this.rawName = rawName;
        this.uuid = uuid;
        this.action = action;
        this.rotation = rotation;
        this.team = team;
        this.time = time;
        this.conf = config;
        this.parentObject = parentObject;
    }
    public HistoryObject(Tile tile, Block actionBlock, String rawName, String uuid, String action, Date time, boolean parentObject) {
        this.tile = tile;
        this.actionBlock = actionBlock;
        this.rawName = rawName;
        this.uuid = uuid;
        this.action = action;
        this.time = time;
    }
    public HistoryObject(Tile tile, Block actionBlock, String rawName, String uuid, String action, Date time, Object config, boolean parentObject) {
        this.tile = tile;
        this.actionBlock = actionBlock;
        this.rawName = rawName;
        this.uuid = uuid;
        this.action = action;
        this.time = time;
        this.conf = config;
    }

}
