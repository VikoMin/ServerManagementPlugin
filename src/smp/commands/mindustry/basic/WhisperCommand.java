package smp.commands.mindustry.basic;

import mindustry.gen.Player;
import smp.commands.BasicCommand;
import smp.errors.MindustryErrors;

import static smp.functions.FindPlayer.findPlayerByName;

public class WhisperCommand extends BasicCommand<Player> {
    public WhisperCommand() {
        super("w", "Whispers to other player", new arc.struct.Seq<String>().add("<plr>").add("<message...>"));
    }

    @Override
    public void run(String[] args, Player parameter) throws MindustryErrors.CommandException {
        if (findPlayerByName(args[0]) == null) throw new MindustryErrors.CommandException("player.not.found", parameter);

        Player plr = findPlayerByName(args[0]);

        plr.sendMessage("[grey]" + parameter.plainName() + " whispers[white]: " + args[1]);
    }
}
