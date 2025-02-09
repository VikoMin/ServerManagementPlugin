package smp.commands;

import arc.struct.Seq;
import arc.util.CommandHandler;
import arc.util.Log;
import mindustry.Vars;
import mindustry.gen.Player;
import smp.commands.discord.DiscordCommand;

import static arc.util.Log.log;

public class CommandRegister<T extends BasicCommand<Player>> {

    public final Seq<T> commands = new Seq<>();
    public static Seq<DiscordCommand> discordCommands = new Seq<>();
    public final Seq<T> adminCommands = new Seq<>();
    public final CommandHandler handler;

    public CommandRegister(CommandHandler handler) {
        this.handler = handler;
    }
    @SafeVarargs
    public final void registerCommand(T... commands) {
        for (var command: commands) {
            if (handler.getCommandList().contains(t -> t.text.equalsIgnoreCase(command.name))) handler.removeCommand(command.name);

            StringBuilder builder = new StringBuilder();

            if (command.isAdmin){
                this.adminCommands.add(command);
            } else {
                this.commands.add(command);
            }

            for (var param : command.params) builder.append(param.rawname).append(" ");

            try{
                handler.register(command.name, builder.toString(), command.desc, command::handle);
                log(Log.LogLevel.info, "Registered command " + command.name);
            }catch(Throwable e){
                log(Log.LogLevel.err, "Unable to register command" + e);
            }
        }
    }
    public static void registerDiscordCommand(DiscordCommand commands) {
        discordCommands.add(commands);
    }
    public void updateCommands(){
        for (CommandHandler.Command cmd : Vars.netServer.clientCommands.getCommandList()){
            Seq<String> params = new Seq<>();
            for (var param : cmd.params) {
                String paramName = param.name;
                if (param.variadic){
                    paramName = paramName + "...";
                }
                if (!param.optional){
                    paramName = "<"+ paramName +">";
                } else {
                    paramName = "[" + paramName + "]";
                }
                params.add(paramName);
            }
            BasicCommand<Player> command = new BasicCommand<>(cmd.text, cmd.description, params);
            if(findCommand(command.name) != null) Log.err("Same command detected! " + command.name);
            else commands.add((T) command);
        }
    }
    public T findCommand(String arg){
        return commands.find(cmd -> cmd.name.equalsIgnoreCase(arg));
    }
    public static String formatParams(BasicCommand<Player> command){
        if (command.params.isEmpty()) return " ";
        StringBuilder builder = new StringBuilder();
        for (var param : command.params) builder.append(param.rawname).append(" ");
        return builder.toString();
    }
}
