package smp.commandSystem.commands.mindustry.basic;

import arc.math.Mathf;
import arc.util.Strings;
import mindustry.gen.Player;
import smp.commandSystem.mindustry.MindustryCommand;
import smp.commandSystem.mindustry.BasicAdminCommand;

import static smp.Main.*;
import static smp.commandSystem.CommandRegister.formatParams;


public class HelpCommand extends MindustryCommand<Player> {

    public HelpCommand() {
        super("help", "Lists all commands.", new arc.struct.Seq<String>().add("[page]"));
    }

    @Override
    public void run(String[] args, Player player) {
            if(args.length > 0 && !Strings.canParseInt(args[0])){
                player.sendMessage("[scarlet]'page' must be a number.");
                return;
            }
            int commandsPerPage = 12;
            int page = args.length > 0 ? Strings.parseInt(args[0]) : 1;
            int pages = Mathf.ceil((float)register.mindustryCommands.size / commandsPerPage);

            page--;

            if(page >= pages || page < 0){
                player.sendMessage("[scarlet]'page' must be a number between[orange] 1[] and[orange] " + pages + "[scarlet].");
                return;
            }

            StringBuilder result = new StringBuilder();
            result.append(Strings.format("[orange]-- Commands Page[lightgray] @[gray]/[lightgray]@[orange] --\n\n", (page + 1), pages));
            if (player.admin) {
                for (int i = commandsPerPage * page; i < Math.min(commandsPerPage * (page + 1), register.adminCommands.size); i++) {
                    BasicAdminCommand command = (BasicAdminCommand) register.adminCommands.get(i);
                    result.append("[orange] /").append(command.name).append("[red] ").append(formatParams(command)).append("[violet] - ").append(command.desc).append("\n");
                }
            }
            for(int i = commandsPerPage * page; i < Math.min(commandsPerPage * (page + 1), register.mindustryCommands.size); i++){
                MindustryCommand<Player> command = register.mindustryCommands.get(i);
                result.append("[orange] /").append(command.name).append("[cyan] ").append(formatParams(command)).append("[lightgray] - ").append(command.desc).append("\n");
            }

            System.out.println(register.mindustryCommands);
            player.sendMessage(result.toString());
        }
    }
