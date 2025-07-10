package smp.discord.embedSystem.embeds;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import smp.functions.Wrappers;
import smp.models.PlayerData;

import java.awt.*;

import static arc.util.Strings.stripColors;

public class StatsEmbed {
    public static EmbedBuilder statsEmbed(PlayerData data) {
        return new EmbedBuilder()
                .setTitle("Information")
                .setColor(Color.RED)
                .addField("Name", stripColors(data.name))
                .addField("ID", String.valueOf(data.id))
                .addField("Rank", data.rank)
                .addField("Achievements", data.achievements.toString())
                .addField("Playtime", Wrappers.minutesFormat(data.playtime));
    }
}
