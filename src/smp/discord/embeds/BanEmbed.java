package smp.discord.embeds;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import smp.models.PlayerData;

import java.awt.*;

public class BanEmbed {
    public static EmbedBuilder banEmbed(PlayerData data, String reason, long banTime, String moderator) {
        return new EmbedBuilder()
                .setTitle("Ban event")
                .setColor(Color.RED)
                .addField("**ID**", String.valueOf(data.id))
                .addField("**Name**", data.name)
                .addField("**Reason**", reason)
                .addField("**Expires**", "<t:" + banTime / 1000 + ":D>")
                .addField("**Moderator**", moderator);
    }
}
