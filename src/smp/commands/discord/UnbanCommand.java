package smp.commands.discord;

import org.javacord.api.event.message.MessageCreateEvent;
import smp.database.FindPlayerData;
import smp.functions.Utilities;
import smp.models.PlayerData;

import java.util.Date;

import static arc.util.Strings.canParseInt;
import static java.lang.Integer.parseInt;
import static smp.functions.Wrappers.formatBanTime;

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
