package smp.commandSystem.commands.discord.admin;

import mindustry.gen.Groups;
import org.javacord.api.event.message.MessageCreateEvent;
import smp.commandSystem.discord.DiscordCommand;
import smp.database.players.FindPlayerData;
import smp.models.PlayerData;

import java.util.Objects;

import static mindustry.Vars.netServer;

public class AdminRewCommand extends DiscordCommand {
    public AdminRewCommand() {
        super("adminrew", " <player> -> Rewokes admin powers from player", 1,true, false);
    }

    @Override
    public void run(MessageCreateEvent listener) {
        String[] args = this.params;
        PlayerData plr = FindPlayerData.getPlayerDataAnyway(args[0]);
        if (plr == null) {listener.getChannel().sendMessage("Could not find that player!"); return;}
        netServer.admins.unAdminPlayer(plr.uuid);
        if (Groups.player.find(p -> Objects.equals(p.uuid(), plr.uuid)) != null){
            Groups.player.find(p -> Objects.equals(p.uuid(), plr.uuid)).admin = false;
        }
        listener.getChannel().sendMessage("Admins powers has been rewoked from " + plr.name);
    }
}
