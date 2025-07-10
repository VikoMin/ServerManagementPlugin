package smp.commandSystem.commands.mindustry.admin;

import arc.Events;
import mindustry.game.EventType;
import mindustry.game.Team;
import mindustry.gen.Player;
import smp.commandSystem.mindustry.BasicAdminCommand;

import static smp.database.players.PlayerFunctions.updateData;

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
