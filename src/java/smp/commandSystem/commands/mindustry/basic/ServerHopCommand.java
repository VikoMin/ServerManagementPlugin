package smp.commandSystem.commands.mindustry.basic;

import mindustry.gen.Player;
import smp.commandSystem.mindustry.MindustryCommand;
import smp.errors.MindustryErrors;

public class ServerHopCommand extends MindustryCommand<Player> {
    public ServerHopCommand() {
        super("serverhop", "Connects you to the other server in this network by port.", new arc.struct.Seq<String>().add("[port]"));
    }

    @Override
    public void run(String[] args, Player parameter) throws MindustryErrors.CommandException {

    }
}
