package smp.commandSystem.commands.mindustry.admin;

import mindustry.gen.Player;
import smp.commandSystem.mindustry.BasicAdminCommand;
import smp.models.PlayerData;

import static arc.util.Strings.canParseInt;
import static arc.util.Strings.parseInt;
import static smp.database.players.FindPlayerData.getPlayerData;
import static smp.errors.MindustryErrors.*;
import static smp.history.History.revert;

public class RevertCommand extends BasicAdminCommand {
    public RevertCommand() {
        super(
                "revert"
                , "Reverts the last broken blocks in certain radius and certain UUID"
                , new arc.struct.Seq<String>().add("<radius>").add("<playerdata>"), true);
    }

    @Override
    public void run(String[] args, Player parameter) throws CommandException {
        PlayerData data;
        if (canParseInt(args[1])) {
            data = getPlayerData(parseInt(args[1]));
        } else {
            data = getPlayerData(args[1]);
        }
        if (data == null) throw new CommandException("data.not.found", parameter);

        if (!canParseInt(args[0])) throw new CommandException("integer.parse.fail", parameter);

        revert(parseInt(args[0]), parameter.tileX(), parameter.tileY(), data.uuid);

    }
}
