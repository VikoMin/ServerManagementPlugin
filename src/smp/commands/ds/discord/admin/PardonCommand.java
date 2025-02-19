package smp.commands.ds.discord.admin;

import mindustry.Vars;
import org.javacord.api.event.message.MessageCreateEvent;
import smp.commands.ds.discord.DiscordCommand;
import smp.database.players.FindPlayerData;
import smp.functions.Utilities;
import smp.models.PlayerData;

import java.util.Date;

import static arc.util.Strings.canParseInt;
import static java.lang.Integer.parseInt;
import static smp.functions.Wrappers.formatBanTime;

public class PardonCommand extends DiscordCommand {
    public PardonCommand() {
        super("pardon", " <id/name/uuid> - Removes the kick effect from player.", 1,true, false);
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

        Vars.netServer.admins.kickedIPs.remove(plr.ip);
        listener.getChannel().sendMessage("Player has been pardoned!");
    }
}
