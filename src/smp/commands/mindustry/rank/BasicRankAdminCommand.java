package smp.commands.mindustry.rank;

import arc.struct.Seq;
import mindustry.gen.Player;
import smp.commands.BasicCommand;
import smp.errors.MindustryErrors;

import static arc.net.ArcNet.handleError;
import static smp.errors.MindustryErrors.commandNotFound;

public class BasicRankAdminCommand extends BasicCommand<Player> {

    public BasicRankAdminCommand(String name, String desc, Seq<String> param, boolean isAdmin, int allowedPriority) {
        super(name, desc, param, isAdmin);
    }

    @Override
    public void run(String[] args, Player player) throws MindustryErrors.CommandException {

    }

    @Override
    public void handle(String[] args, Player parameter){
        try{
            if (parameter.admin) {
                run(args, parameter);
            }
            else {
             commandNotFound(parameter);
            }
        }catch(Exception exception){
            handleError(exception);
        }
    }
}
