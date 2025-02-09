package smp.commands.discord;

import mindustry.gen.Groups;
import mindustry.gen.Player;
import org.javacord.api.event.message.MessageCreateEvent;
import smp.database.FindPlayerData;
import smp.models.PlayerData;

import java.util.Arrays;

import static mindustry.Vars.netServer;
import static smp.database.PlayerFunctions.updateData;
import static smp.other.Ranks.ranks;

public class SetRankCommand extends DiscordCommand {
    public SetRankCommand() {
        super("setrank", " <rank> <playerID> -> Sets rank of the player.", 2,false, true);
    }

    @Override
    public void run(MessageCreateEvent listener) {
        String[] args = this.params;
        PlayerData plr = FindPlayerData.getPlayerData(args[1]);
        if (plr == null) {listener.getChannel().sendMessage("This player does not exist!"); return;}
        plr.changeValue("rank", args[0]);
        updateData(FindPlayerData.getPlayerData(args[1]), plr);
        listener.getChannel().sendMessage("Rank of " + plr.name + " has been changed to " + plr.rank);
    }
}
