package smp.discord.embedSystem.embeds;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import smp.models.Punishment;

import java.awt.*;

public class PunishmentEmbed {
    public static EmbedBuilder punishmentEmbed(Punishment punishment) {

        return new EmbedBuilder()
                .setTitle("Punishment")
                .setColor(Color.RED)
                .addField("**Punishment Type**", punishment.punishmentType)
                .addField("**Reason**", punishment.punishmentReason)
                .addField("**Expires**", "<t:" + punishment.punishmentDuration + ">", true)
                .addField("**Moderator**", punishment.punishmentModerator);
    }
}
