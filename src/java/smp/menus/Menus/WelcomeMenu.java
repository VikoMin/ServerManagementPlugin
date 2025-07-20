package smp.menus.Menus;

import mindustry.gen.Call;
import smp.menus.Menu;

import static mindustry.ui.Menus.registerMenu;
import static smp.vars.Variables.*;

public class WelcomeMenu extends Menu {
    public WelcomeMenu(){
        super(
                welcomeMenuTitle,
                welcomeMenuText,
                new String[][]{{"[red]Close", "[blue]Our Discord"}},
                registerMenu((player, option) -> {
                    switch(option){
                        case -1, 0 -> {
                        }
                        case 1 -> Call.openURI(discordURL);
                    }
                })
        );
    }
}
