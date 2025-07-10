package smp.commandSystem.commands.discord.basic;

import org.javacord.api.event.message.MessageCreateEvent;
import smp.commandSystem.discord.DiscordCommand;
import smp.database.players.FindPlayerData;
import smp.models.PlayerData;

import static arc.util.Strings.canParseInt;
import static java.lang.Integer.parseInt;
import static smp.discord.embedSystem.embeds.StatsEmbed.statsEmbed;

public class StatsCommand extends DiscordCommand {
    public StatsCommand() {
        super("stats", " <id/name/uuid...> - Views the statistic of the player.", 1);
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
        listener.getChannel().sendMessage(statsEmbed(plr));

    }
}
