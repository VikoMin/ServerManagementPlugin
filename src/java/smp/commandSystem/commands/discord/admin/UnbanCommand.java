package smp.commandSystem.commands.discord.admin;

import org.javacord.api.event.message.MessageCreateEvent;
import smp.commandSystem.discord.DiscordCommand;
import smp.database.players.FindPlayerData;
import smp.functions.Utilities;
import smp.models.PlayerData;

import static arc.util.Strings.canParseInt;
import static java.lang.Integer.parseInt;

public class UnbanCommand extends DiscordCommand {
    public UnbanCommand() {
        super("unban", " <id/name/uuid> -> Unbans the player.", 1,true, false);
    }

    @Override
    public void run(MessageCreateEvent listener) {
        String[] args = this.params;
        PlayerData plr = null;

        if (canParseInt(args[0])) {
            plr = FindPlayerData.getPlayerData(parseInt(args[0]));
        } else {
            plr = FindPlayerData.getPlayerData(args[0]);
        }

        if (plr == null) {listener.getChannel().sendMessage("Could not find that player!"); return;}

        Utilities.unbanPlayer(plr);
        listener.getChannel().sendMessage("Unbanned: " + plr.rawName);
    }
}
