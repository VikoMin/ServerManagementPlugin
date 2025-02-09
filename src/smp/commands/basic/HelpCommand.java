package smp.commands.basic;

import arc.math.Mathf;
import arc.util.Strings;
import mindustry.gen.Player;
import smp.commands.BasicCommand;
import smp.commands.admin.BasicAdminCommand;

import static arc.util.Log.err;
import static arc.util.Log.info;
import static smp.Main.*;
import static smp.commands.CommandRegister.formatParams;


public class HelpCommand extends BasicCommand<Player> {

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
            int pages = Mathf.ceil((float)register.commands.size / commandsPerPage);

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

            for(int i = commandsPerPage * page; i < Math.min(commandsPerPage * (page + 1), register.commands.size); i++){
                BasicCommand command = (BasicCommand) register.commands.get(i);
                result.append("[orange] /").append(command.name).append("[cyan] ").append(formatParams(command)).append("[lightgray] - ").append(command.desc).append("\n");
            }
            player.sendMessage(result.toString());
        };
    }
