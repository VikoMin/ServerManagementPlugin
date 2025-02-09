package smp.commands.admin;

import arc.Events;
import mindustry.game.EventType;
import mindustry.game.Team;
import mindustry.gen.Player;
import smp.errors.MindustryErrors;
import smp.models.PlayerData;

import static smp.database.FindPlayerData.getPlayerDataAnyway;
import static smp.database.PlayerFunctions.updateData;

public class GameoverCommand extends BasicAdminCommand {
    public GameoverCommand() {
        super("gameover", "Executes gameover event.", new arc.struct.Seq<>(), true);
    }

    @Override
    public void run(String[] args, Player parameter) {
        Events.fire(new EventType.GameOverEvent(Team.derelict));
        parameter.sendMessage("[green]Gameover executed!");
    }
}
