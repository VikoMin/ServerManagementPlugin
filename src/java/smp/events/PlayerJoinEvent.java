package smp.events;

import arc.util.Log;
import arc.util.Strings;
import mindustry.gen.Call;
import mindustry.gen.Player;
import smp.menus.Menus.WelcomeMenu;
import smp.models.PlayerData;

import static smp.database.players.PlayerChecks.DisplayPlayerRank;
import static smp.database.players.PlayerFunctions.fillData;
import static smp.database.players.PlayerFunctions.findPlayerDataOrCreate;
import static smp.functions.Checks.kickIfBanned;
import static smp.vars.Variables.welcomeMenuEnabled;

public class PlayerJoinEvent {
    public static WelcomeMenu welcomeMenu = new WelcomeMenu();

    public static void joinEvent(Player plr){
        PlayerData data = findPlayerDataOrCreate(plr);
        fillData(data, plr);
        kickIfBanned(plr);
        String joinMessage = data.joinMessage;
        DisplayPlayerRank(plr.uuid());
        Call.sendMessage(Strings.format(joinMessage + " [grey][" + data.id + "]", plr.name()));
        Log.info(plr.plainName() + " joined! " + "[" + data.id + "]");
        if (welcomeMenuEnabled) {
            welcomeMenu.run(plr.con());
        }
    }
}
