package smp.commands.mindustry.admin;

import arc.struct.Seq;
import mindustry.gen.Player;
import smp.commands.BasicCommand;
import smp.errors.MindustryErrors;

import static arc.net.ArcNet.handleError;
import static smp.errors.MindustryErrors.commandNotFound;

public class BasicAdminCommand extends BasicCommand<Player> {

    public BasicAdminCommand(String name, String desc, Seq<String> param, boolean isAdmin) {
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
