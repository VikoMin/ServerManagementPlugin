package smp.antigrief;

import arc.Events;
import arc.math.geom.Point2;
import arc.struct.IntSeq;
import arc.util.Log;
import mindustry.content.Blocks;
import mindustry.game.EventType;
import mindustry.gen.Call;
import mindustry.gen.Groups;
import mindustry.gen.Player;

public class NodeGriefingWarning {
    public static void initializeNodeGriefingWarnings() {
        Events.on(EventType.ConfigEvent.class, event -> {
            if (event.tile.block == Blocks.powerNode || event.tile.block == Blocks.powerNodeLarge) {
                if (event.tile.buildOn().power.links.isEmpty() && !(event.value instanceof Integer) && event.player != null) {
                    String str = "[orange]Warning! Possible node griefing at: "
                            + event.tile.tileX()
                            + " "
                            + event.tile.tileY() + " by: "
                            + event.player.coloredName();
                    Groups.player.each(Player::admin, a -> a.sendMessage(str));
                }
            }
        });
    }
}
