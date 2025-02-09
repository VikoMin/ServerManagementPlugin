package smp.errors;

import mindustry.gen.Player;

public class MindustryErrors {
    public static void noPlayerFound(Player player){
        player.sendMessage("[red]No player found!");
    }
    public static void commandNotFound(Player player){
        player.sendMessage("[red]Command Not Found!");
    }
    public static void noDataFound(Player player){
        player.sendMessage("[red]Data Not Found!");
    }
    public static void stringCantBeAnNumber(Player player){
        player.sendMessage("[red]You cant turn text into number!");
    }

    public static class CommandException extends Exception {
        public CommandException(String err, Player player){
            switch (err){
                case "player.not.found": noPlayerFound(player);
                case "command.not.found": commandNotFound(player);
                case "data.not.found": noDataFound(player);
                case "integer.parse.fail": stringCantBeAnNumber(player);
            }
        }
    }
}
