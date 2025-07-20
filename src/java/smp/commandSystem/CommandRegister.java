package smp.commandSystem;

import arc.struct.Seq;
import arc.util.CommandHandler;
import arc.util.Log;
import mindustry.Vars;
import mindustry.gen.Player;
import smp.commandSystem.discord.DiscordCommand;
import smp.commandSystem.mindustry.MindustryCommand;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

import static arc.util.Log.log;
import static smp.functions.Utilities.notNullElse;
import static smp.system.config.ConfigSystem.*;
import static smp.system.reflect.ReflectSystem.exportClasses;
import static smp.vars.Variables.allCommandsEnabled;
import static smp.vars.Variables.allDiscordCommandsEnabled;

public class CommandRegister<T extends MindustryCommand<Player>> {

    public final Seq<T> mindustryCommands = new Seq<>();
    public static Seq<DiscordCommand> discordCommands = new Seq<>();
    public final Seq<T> adminCommands = new Seq<>();
    public final CommandHandler handler;

    public CommandRegister(CommandHandler handler) {
        this.handler = handler;
    }

    public final void registerCommands(List<T> commands) {
        Set<String> whitelistedCommands = new HashSet<>();
        if (!allCommandsEnabled){
            whitelistedCommands = booleans.keySet().stream().filter(k -> k.contains("mindustry.commands.command-whitelist.")).collect(Collectors.toSet());
            System.out.println(whitelistedCommands);
        }
        for (var command: commands) {
            if (!whitelistedCommands.isEmpty() && !whitelistedCommands.contains("mindustry.commands.command-whitelist." + command.name)){
                Log.warn("command has not been registered: " + command.name);
                continue;
            }

            if (handler.getCommandList().contains(t -> t.text.equalsIgnoreCase(command.name))) handler.removeCommand(command.name);

            StringBuilder builder = new StringBuilder();

            if (command.isAdmin){
                this.adminCommands.add(command);
            } else {
                this.mindustryCommands.add(command);
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

    public static void registerDiscordCommands(List<DiscordCommand> commands) {
        for (var command : commands) {
            if (!getConfigField("discord.commands.command-whitelist." + command.name, booleans) && !allDiscordCommandsEnabled) {
                Log.warn("Discord command has not been registered: " + command.name);
                return;
            }
            discordCommands.add(command);
        }
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
            MindustryCommand<Player> command = new MindustryCommand<>(cmd.text, cmd.description, params);
            if (findCommand(command.name) != null)
                Log.err("Same command detected! " + command.name);
            else {
                mindustryCommands.add((T) command);
            }
        }
    }

    public T findCommand(String arg){
        return notNullElse(mindustryCommands.find(cmd -> cmd.name.equalsIgnoreCase(arg)), adminCommands.find(cmd -> cmd.name.equalsIgnoreCase(arg)));
    }

    public static List<?> getCommandsFromPackage(String pckg){
        try {
            List<Class<?>> classes = exportClasses(pckg);
            List<Object> commands = new ArrayList<>();
            for (Class<?> clazz : classes){
                commands.add(clazz.getConstructors()[0].newInstance());
            }
            return commands;
        } catch (IOException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }

    public static String formatParams(MindustryCommand<Player> command){
        if (command.params.isEmpty()) return " ";
        StringBuilder builder = new StringBuilder();
        for (var param : command.params) builder.append(param.rawname).append(" ");
        return builder.toString();
    }
}
