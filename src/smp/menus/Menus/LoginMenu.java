package smp.menus.Menus;

import org.javacord.api.event.message.MessageCreateEvent;
import smp.database.players.PlayerFunctions;
import smp.menus.Menu;
import smp.models.PlayerData;

import static mindustry.ui.Menus.registerMenu;

public class LoginMenu extends Menu {
    public LoginMenu(PlayerData data, MessageCreateEvent listener){
        super(
                "Link request",
                listener.getMessageAuthor().getName() + " wants to link discord account to your mindustry account.",
                new String[][]{{"[green]Accept", "[red]Decline"}},
                registerMenu((player, option) -> {
                    switch(option){
                        case -1 -> {return;}
                        case 0 -> {
                            data.set("discordId", String.valueOf(listener.getMessageAuthor().getId()));
                            PlayerFunctions.updateData(data);
                            listener.getChannel().sendMessage("Account has been linked to: " + data.id);
                            player.sendMessage("[green]Your account has been linked!");
                        }
                        case 1 -> {
                            listener.getChannel().sendMessage("Your account link offer has been declined!");
                        }
                    }
                })
        );
    }
}
