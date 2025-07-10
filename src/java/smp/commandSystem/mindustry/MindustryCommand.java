package smp.commandSystem.mindustry;

import arc.struct.OrderedSet;
import arc.struct.Seq;
import smp.commandSystem.CommandParameter;
import smp.errors.MindustryErrors;

import static arc.net.ArcNet.handleError;

public class MindustryCommand<T> {
    public final String name, desc;
    public final OrderedSet<CommandParameter> params;
    public boolean isAdmin = false;

    public MindustryCommand(String name, String desc, Seq<String> params) {
        this.name = name;
        this.desc = desc;
        this.params = parseParams(params);
    }
    public MindustryCommand(String name, String desc) {
        this.name = name;
        this.desc = desc;
        this.params = new OrderedSet<>();
    }

    public MindustryCommand(String name, String desc, Seq<String> params, boolean isAdmin) {
        this.name = name;
        this.desc = desc;
        this.params = parseParams(params);
        this.isAdmin = isAdmin;
    }

    public void handle(String[] args, T parameter){
        try{
            run(args, parameter);
        }catch(Exception exception){
            handleError(exception);
        }
    }

    public void run(String[] args, T parameter) throws MindustryErrors.CommandException {

    }

    private OrderedSet<CommandParameter> parseParams(Seq<String> param){
        OrderedSet<CommandParameter> params = new OrderedSet<>();
        for (var entry : param) params.add(new CommandParameter(entry));
        return params;
    }

}


