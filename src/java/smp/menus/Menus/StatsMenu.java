package smp.menus.Menus;

import smp.menus.Menu;
import smp.models.PlayerData;

import static mindustry.ui.Menus.registerMenu;
import static smp.functions.Wrappers.statsWrapper;

public class StatsMenu extends Menu {
    public StatsMenu(PlayerData data){
        super(
                "Statistics",
                statsWrapper(data),
                new String[][]{{"[red]Close"}},
                registerMenu((player, option) -> {
                    switch(option){
                        case -1: return;
                        case 0: return;
                    }
                })
        );
    }
}
