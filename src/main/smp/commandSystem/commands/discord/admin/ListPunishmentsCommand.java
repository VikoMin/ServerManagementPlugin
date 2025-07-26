package smp.commandSystem.commands.discord.admin;

import org.javacord.api.event.message.MessageCreateEvent;
import smp.commandSystem.discord.DiscordCommand;
import smp.database.players.FindPlayerData;
import smp.models.PlayerData;
import smp.models.Punishment;

import static arc.util.Strings.canParseInt;
import static java.lang.Integer.parseInt;
import static smp.discord.embedSystem.embeds.PunishmentEmbed.punishmentEmbed;

public class ListPunishmentsCommand extends DiscordCommand {
    public ListPunishmentsCommand() {
        super("listpunishments", " <id/name/uuid> -> Shows punishments of the player.", 1,true, false);
    }

    @Override
    public void run(MessageCreateEvent listener) {
        String[] args = this.params;
        PlayerData plr;
        System.out.println(args[0]);
        if (canParseInt(args[0])) {
            plr = FindPlayerData.getPlayerData(parseInt(args[0]));
        } else {
            plr = FindPlayerData.getPlayerData(args[0]);
        }
        for (Punishment punishment : plr.punishments){
            listener.getChannel().sendMessage(punishmentEmbed(punishment));
        }

    }
}
