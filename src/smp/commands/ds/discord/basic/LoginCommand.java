package smp.commands.ds.discord.basic;

import mindustry.gen.Player;
import org.javacord.api.event.message.MessageCreateEvent;
import smp.commands.ds.discord.DiscordCommand;
import smp.menus.Menus.LoginMenu;
import smp.models.PlayerData;

import static smp.database.players.FindPlayerData.getPlayerData;
import static smp.functions.FindPlayer.findPlayerByName;

public class LoginCommand extends DiscordCommand {
    public LoginCommand() {
        super("login", " <playerName> -> Links your discord account to mindustry (You must be in-game to link your account).", 1);
    }

    @Override
    public void run(MessageCreateEvent listener) {
        String[] args = this.params;
        Player player = findPlayerByName(args[0]);

        if (player == null) {listener.getChannel().sendMessage("Could not find that player!"); return;}

        PlayerData data = getPlayerData(player.uuid());
        LoginMenu menu = new LoginMenu(data, listener);

        menu.run(player.con);
    }
}
