package smp.commands.ds.discord.admin;

import org.javacord.api.event.message.MessageCreateEvent;
import smp.commands.ds.discord.DiscordCommand;
import smp.database.players.FindPlayerData;
import smp.functions.Utilities;
import smp.models.PlayerData;
import smp.models.Punishment;

import java.util.Date;

import static arc.util.Strings.canParseInt;
import static java.lang.Integer.parseInt;
import static smp.functions.Wrappers.formatBanTime;

public class ListPunishmentsCommand extends DiscordCommand {
    public ListPunishmentsCommand() {
        super("listpunishments", " <id/name/uuid> -> Shows punishments of the player.", 1,true, false);
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
        StringBuilder builder = new StringBuilder();
        for (Punishment punishment : plr.punishments){
            builder.append("Punishment type: ").append(punishment.punishmentType).append("\n");
            builder.append("Punishment reason: ").append(punishment.punishmentReason).append("\n");
            builder.append("Punishment expire date: ").append("<t:").append(punishment.punishmentDuration).append(">").append("\n");
            builder.append("Punishment moderator: ").append(punishment.punishmentModerator).append("\n");
            builder.append("\n\n");
        }
        listener.getChannel().sendMessage(builder.toString());
    }
}
