package smp.commandSystem.commands.discord.admin;

import org.javacord.api.event.message.MessageCreateEvent;
import smp.commandSystem.discord.DiscordCommand;
import smp.database.players.FindPlayerData;
import smp.models.PlayerData;
import smp.models.Punishment;

import static arc.util.Strings.canParseInt;
import static java.lang.Integer.parseInt;
import static smp.database.DatabaseSystem.updateDatabaseDocument;
import static smp.database.DatabaseSystem.playerCollection;
import static smp.database.punishments.FindPunishment.findPunishmentLastBan;

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

        unbanPlayer(plr);
        listener.getChannel().sendMessage("Unbanned: " + plr.rawName);
    }

    public static void unbanPlayer(PlayerData data){
        Punishment punishment = findPunishmentLastBan(data);
        data.punishments.remove(punishment);
        punishment.punishmentType = "unbanned";
        data.punishments.add(punishment);
        updateDatabaseDocument(data, playerCollection, "_id", data.id);
    }
}
