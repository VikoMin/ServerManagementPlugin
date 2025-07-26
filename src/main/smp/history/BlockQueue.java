package smp.history;

import mindustry.game.Team;
import mindustry.world.Block;

public class BlockQueue {
    final int x;
    final int y;
    final Block block;
    final Team team;
    final int rotat;
    final boolean hard;
    Object configurations;

    public BlockQueue(int x, int y, Block block, Team team, int rotat, boolean hard, Object config) {
        this.x = x;
        this.y = y;
        this.block = block;
        this.team = team;
        this.rotat = rotat;
        this.hard = hard;
        this.configurations = config;
    }
}

