package smp.commands.ds.discord.admin;

import org.javacord.api.entity.message.embed.Embed;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import smp.commands.ds.discord.DiscordCommand;
import smp.database.players.FindPlayerData;
import smp.functions.Utilities;
import smp.models.PlayerData;
import smp.models.Punishment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static arc.util.Strings.canParseInt;
import static java.lang.Integer.parseInt;
import static smp.discord.embeds.PunishmentEmbed.punishmentEmbed;
import static smp.functions.Wrappers.formatBanTime;

public class ListPunishmentsCommand extends DiscordCommand {
    public ListPunishmentsCommand() {
        super("listpunishments", " <id/name/uuid> -> Shows punishments of the player.", 1,true, false);
    }

    @Override
    public void run(MessageCreateEvent listener) {
        String[] args = this.params;
        PlayerData plr = null;
        System.out.println(args[0]);
        if (canParseInt(args[0])) {
            plr = FindPlayerData.getPlayerData(parseInt(args[0]));
        } else {
            plr = FindPlayerData.getPlayerData(args[0]);
        }
        for (Punishment punishment : plr.punishments){
            System.out.println("yaya");
            listener.getChannel().sendMessage(punishmentEmbed(punishment));
        }

    }
}
