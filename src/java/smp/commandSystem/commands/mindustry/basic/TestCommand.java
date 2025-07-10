package smp.commandSystem.commands.mindustry.basic;

import mindustry.gen.Call;
import mindustry.gen.Player;
import smp.commandSystem.mindustry.MindustryCommand;

public class TestCommand extends MindustryCommand<Player> {
    public TestCommand() {
        super("test", "desc");
    }

    @Override
    public void run(String[] args, Player plr) {
        Call.announce("test " + plr.name());
    }
}
