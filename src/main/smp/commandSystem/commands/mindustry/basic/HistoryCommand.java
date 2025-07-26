package smp.commandSystem.commands.mindustry.basic;

import mindustry.gen.Call;
import mindustry.gen.Player;
import smp.commandSystem.mindustry.MindustryCommand;

import static smp.history.TileLogSystem.historyPlayers;

public class HistoryCommand extends MindustryCommand<Player> {
    public HistoryCommand() {
        super("history", "Enables/Disables history mode");
    }

    @Override
    public void run(String[] args, Player plr) {
        if (!historyPlayers.contains(plr)){
            historyPlayers.add(plr);
            plr.sendMessage("[green]History enabled!");
        } else {
            historyPlayers.remove(plr);
            plr.sendMessage("[red]History disabled!");
            Call.hideHudText(plr.con());
        }
    }
}
