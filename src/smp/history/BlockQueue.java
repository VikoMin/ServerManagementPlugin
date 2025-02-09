package smp.history;

import arc.func.Cons2;
import arc.struct.ObjectMap;
import mindustry.game.Team;
import mindustry.world.Block;

public class BlockQueue {
    final int x;
    final int y;
    final Block block;
    final Team team;
    final int rotat;
    Object configurations;

    public BlockQueue(int x, int y, Block block, Team team, int rotat, Object config) {
        this.x = x;
        this.y = y;
        this.block = block;
        this.team = team;
        this.rotat = rotat;
        this.configurations = config;
    }
}

