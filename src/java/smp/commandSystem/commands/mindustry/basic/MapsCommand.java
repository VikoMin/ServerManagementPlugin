package smp.commandSystem.commands.mindustry.basic;

import arc.struct.Seq;
import mindustry.gen.Player;
import mindustry.maps.Map;
import smp.commandSystem.mindustry.MindustryCommand;

import java.util.Arrays;

import static arc.util.Strings.canParseInt;
import static smp.functions.Utilities.getMaps;

public class MapsCommand extends MindustryCommand<Player> {
    public MapsCommand() {
        super("maps", "Shows server maps",  new arc.struct.Seq<String>().add("[page]"));
    }

    @Override
    public void run(String[] args, Player player) {
        StringBuilder list = new StringBuilder();
        int page;
        if (Arrays.asList(args).isEmpty()){
            page = 0;
        } else {
            if (!canParseInt(args[0])){
                player.sendMessage("[red]Page must be number!");
                return;
            }
            page = Integer.parseInt(args[0]);
        }
        int mapsPerPage = 10;
        Seq<Map> maps = getMaps();
        maps.list().stream().skip(page*10L).limit(mapsPerPage + (page * 10L)).forEach(
                map -> list.append(map.name()).append("[white], by ").append(map.author()).append("\n")
        );
        if (!String.valueOf(list).contains("by")){
            player.sendMessage("[red]No maps detected!");
            return;
        }
        player.sendMessage(String.valueOf(list));
    }
}
