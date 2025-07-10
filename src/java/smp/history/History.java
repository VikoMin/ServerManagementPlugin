package smp.history;

import arc.Events;
import arc.graphics.Color;
import arc.math.geom.Geometry;
import arc.struct.Seq;
import arc.util.Log;
import arc.util.Timer;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.game.EventType;
import mindustry.gen.Building;
import mindustry.gen.Call;
import mindustry.gen.Groups;
import mindustry.gen.Player;
import mindustry.world.Tile;
import mindustry.world.blocks.ConstructBlock;

import javax.sound.midi.SysexMessage;
import java.util.Date;
import java.util.Objects;

import static mindustry.Vars.emptyTile;
import static mindustry.Vars.world;

public class History {
    static Seq<HistoryTile> historyTilesSeq = new Seq<>();
    public static Seq<String> historyPlayers = new Seq<>();
    public static Seq<BlockQueue> blockQueueSeq = new Seq<>();
    public static void loadHistory(){
        Events.on(EventType.BlockBuildBeginEvent.class, event -> {

            Building building = event.tile.build;
            if (!event.unit.isPlayer()) return;
            int block = event.tile.build instanceof ConstructBlock.ConstructBuild build ? build.current.id : event.tile.blockID();

            if (event.breaking){
                HistoryObject obj = new HistoryObject(event.tile
                        , Vars.content.block(block)
                        , event.unit.getPlayer().name
                        , event.unit.getPlayer().uuid()
                        , "broke"
                        , building.rotation
                        , building.team
                        , new Date()
                        , building.config());
                add(obj);
            }
        });
        Events.on(EventType.BlockBuildEndEvent.class, event -> {

            Building building = event.tile.build;
            int block = event.tile.build instanceof ConstructBlock.ConstructBuild build ? build.current.id : event.tile.blockID();
            if (!event.unit.isPlayer()) return;
            if (!event.breaking) {
                if (building != null) {
                    HistoryObject obj = new HistoryObject(event.tile
                            , Vars.content.block(block)
                            , event.unit.getPlayer().name
                            , event.unit.getPlayer().uuid()
                            , "built", new Date()
                            , building.config());
                    add(obj);
                } else {
                    HistoryObject obj = new HistoryObject(event.tile
                            , Vars.content.block(block)
                            , event.unit.getPlayer().name
                            , event.unit.getPlayer().uuid()
                            , "built", new Date());
                    add(obj);
                }
            }

        });

        Events.on(EventType.BuildRotateEvent.class, event -> {

            Building building = event.build;
            int block = event.build instanceof ConstructBlock.ConstructBuild build ? build.current.id : event.build.tile.blockID();
            if (!event.unit.isPlayer()) return;
            HistoryObject obj = new HistoryObject(event.build.tile
                    , Vars.content.block(block)
                    , event.unit.getPlayer().name
                    , event.unit.getPlayer().uuid()
                    , "rotated"
                    , new Date()
                    , building.config());
            add(obj);

        });

        Events.on(EventType.ConfigEvent.class, event -> {

            Building building = event.tile.buildOn();
            int block = event.tile instanceof ConstructBlock.ConstructBuild build ? build.current.id : event.tile.block().id;
            if (event.player == null) return;
            HistoryObject obj = new HistoryObject(event.tile.tile
                    , Vars.content.block(block)
                    , event.player.name
                    , event.player.uuid()
                    , "configured"
                    , new Date()
                    , building.config());
            add(obj);

        });
        // The thing that displays history, may be kinda laggy
        Events.run(EventType.Trigger.update, () -> {
            for (Player plr : Groups.player){
                if (historyPlayers.contains(plr.uuid())) {
                    Tile Eventtile = world.tileWorld(plr.mouseX, plr.mouseY);
                    if (Eventtile != null) {
                        StringBuilder list = new StringBuilder();
                        HistoryTile tile = getTile(Eventtile);
                        Seq<HistoryObject> reversed = new Seq<>(tile.objectSeq);
                        reversed.reverse();
                        for (HistoryObject obj : reversed) {
                            if (obj.tile == world.tileWorld(plr.mouseX, plr.mouseY)) {
                                list.append(format(obj));
                            }
                        }
                        list.append("[")
                                .append(Eventtile.x)
                                .append(", ")
                                .append(Eventtile.y)
                                .append("]");
                        /*Call.infoPopup(plr.con(), list.toString(), 0.017f, Align.center | Align.left, 0, 0, 0, 0);*/
                        Call.setHudText(plr.con(), list.toString());
                    }
                }
            }
        });
        // once gameover, it clears all historytiles
        Events.on(EventType.GameOverEvent.class, event -> {
            historyTilesSeq.clear();
        });
        Log.info("History initialized");
    }


    public static void add(HistoryObject obj){
        if(obj.tile == emptyTile) return;
        // linked is used for logging actions on blocks that are bigger than 2
        obj.tile.getLinkedTiles(other -> {
            HistoryObject newObj = new HistoryObject(other, obj.actionBlock, obj.rawName, obj.uuid, obj.action, obj.rotation, obj.team, obj.time, obj.conf);
            HistoryTile oldTile = getTile(other), newTile = getTile(other);
            Seq<HistoryObject> objectSeq = new Seq<>(oldTile.objectSeq);
            if (newObj.conf != null) newTile.latestConfig = newObj.conf;
            newObj.conf = null;
            objectSeq.add(newObj);
            // limits the size of historyobjects in tile
            if (objectSeq.size > 7){
                objectSeq.remove(0);
            }
            newTile.objectSeq = objectSeq;
            if (newTile.tileX == obj.tile.worldx() && newTile.tileY == obj.tile.worldy()) newTile.parentTile = true;
            if (historyTilesSeq.contains(oldTile)){
                historyTilesSeq.replace(oldTile, newTile);
            } else {
                historyTilesSeq.add(newTile);
            }
        });
    }
    // formats the raw history objects, so it will be readable for human eye
    public static String format(HistoryObject obj){
        return (obj.time.getHours() +":"+ obj.time.getMinutes() +":"+ obj.time.getSeconds() + " " +obj.rawName + "[white] " + obj.action + " " + obj.actionBlock.emoji() + "\n");
    }

    public static HistoryTile getTile(Tile tile){
        for (HistoryTile histTile : historyTilesSeq){
            if (histTile.tileX == tile.worldx() && histTile.tileY == tile.worldy()){
                return histTile;
            }
        }
        return new HistoryTile(tile.worldx(), tile.worldy());
    }

    public static void addToQueue(BlockQueue q){
        blockQueueSeq.add(q);
    }

    public static void revert(int radius, int x, int y, String uuid){
        Geometry.circle(x ,y, radius, (cx, cy) -> {
            if (world.tile(cx, cy) != null) {
                HistoryTile tile = getTile(world.tile(cx, cy));
                if (tile.parentTile) {
                    HistoryObject object = formatHistorySeq(new Seq<>(tile.objectSeq), uuid);
                    if (object != null) {
                        BlockQueue queue = new BlockQueue(cx, cy, object.actionBlock, object.team, object.rotation, tile.latestConfig);
                        addToQueue(queue);
                        tile.parentTile=false;
                    }
                }
            }
        });
    }

    public static HistoryObject formatHistorySeq(Seq<HistoryObject> seq, String uuid){
        seq.reverse();
        for (HistoryObject obj : seq) {
            System.out.println("removed:" + obj.uuid + "   " + uuid);
            if (!Objects.equals(obj.action, "broke") || !obj.uuid.equals(uuid)) {
                seq.remove(obj);
            }
        }
        if (!seq.isEmpty()) {
            return seq.get(0);
        } else{
            return null;
        }
    }

    public static void loadRevert(){
        Timer.schedule(() -> {
            if (!blockQueueSeq.isEmpty()) {
                BlockQueue queue = blockQueueSeq.min(blockQueue -> blockQueue.block.size);
                if (world.tile(queue.x, queue.y).build == null && queue.team != null) {
                    Call.effect(Fx.placeBlock, (queue.x) * 8, (queue.y) * 8, queue.block.size, Color.violet);
                    world.tile(queue.x, queue.y).setNet(queue.block, queue.team, queue.rotat);
                    world.tile(queue.x, queue.y).build.configureAny(queue.configurations);
                }
                blockQueueSeq.remove(queue);
            }
        }, 0, 0.016f);
        Log.info("Revert initialized");
    }
}
