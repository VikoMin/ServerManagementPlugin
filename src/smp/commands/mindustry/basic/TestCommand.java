package smp.commands.mindustry.basic;

import mindustry.gen.Call;
import mindustry.gen.Player;
import smp.commands.BasicCommand;

public class TestCommand extends BasicCommand<Player> {
    public TestCommand() {
        super("test", "desc");
    }

    @Override
    public void run(String[] args, Player plr) {
        Call.announce("test " + plr.name());
    }
}
