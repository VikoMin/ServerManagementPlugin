package smp.menus.Menus;

import mindustry.gen.Call;
import mindustry.gen.Player;
import org.javacord.api.event.message.MessageCreateEvent;
import smp.database.players.PlayerFunctions;
import smp.menus.Menu;
import smp.models.PlayerData;

import static mindustry.ui.Menus.registerMenu;
import static smp.Variables.discordURL;

public class WelcomeMenu extends Menu {
    public WelcomeMenu(){
        super(
                "\uE86B Welcome!",
                "[orange]Welcome to our server!\n\n" +
                "[red]<[orange]Basic Rules[red]>\n" +
                "[#f]\uE815 [orange]Do not grief or sabotage your team.\n" +
                "[#f]\uE815 [orange]Do not build/write any NSFW or offensive content.\n" +
                "[#f]\uE815 [orange]Do not try lag the server using lag machines or similar stuff.\n" +
                "[green]\uE800 [orange]Use common sense, do not be toxic/mean to others.\n\n" +
                "[orange]Write /help to see all commands that are available on server.\n" +
                "[blue]\uE80D Also make sure to join our discord.",
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
