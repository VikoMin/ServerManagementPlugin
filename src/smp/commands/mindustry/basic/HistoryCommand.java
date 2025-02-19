package smp.commands.mindustry.basic;

import mindustry.gen.Call;
import mindustry.gen.Player;
import smp.commands.BasicCommand;

import static smp.history.History.historyPlayers;

public class HistoryCommand extends BasicCommand<Player> {
    public HistoryCommand() {
        super("history", "Enables/Disables history mode");
    }

    @Override
    public void run(String[] args, Player plr) {
        if (!historyPlayers.contains(plr.uuid())){
            historyPlayers.add(plr.uuid());
            plr.sendMessage("[green]History enabled!");
        } else {
            historyPlayers.remove(plr.uuid());
            plr.sendMessage("[red]History disabled!");
            Call.hideHudText(plr.con());
        }
    }
}
