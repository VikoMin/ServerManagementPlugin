package smp.commandSystem.commands.mindustry.basic;

import mindustry.gen.Player;
import smp.commandSystem.mindustry.MindustryCommand;
import smp.database.players.FindPlayerData;
import smp.errors.MindustryErrors;
import smp.menus.Menus.StatsMenu;
import smp.models.PlayerData;

public class StatsCommand extends MindustryCommand<Player> {
    public StatsCommand() {
        super("stats", "Shows statistics of player", new arc.struct.Seq<String>().add("<plr...>"));
    }

    @Override
    public void run(String[] args, Player parameter) throws MindustryErrors.CommandException {
        if (FindPlayerData.getPlayerDataAnyway(args[0]) == null) throw new MindustryErrors.CommandException("player.not.found", parameter);
        PlayerData data = FindPlayerData.getPlayerDataAnyway(args[0]);

        StatsMenu menu = new StatsMenu(data);
        menu.run(parameter.con);
    }
}
