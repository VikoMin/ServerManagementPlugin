package smp.commands.discord;

import mindustry.gen.Groups;
import mindustry.gen.Player;
import org.javacord.api.event.message.MessageCreateEvent;

import static mindustry.Vars.netServer;

public class AdminAddCommand extends DiscordCommand {
    public AdminAddCommand() {
        super("adminadd", " <player> -> Gives admin to the player.", 1,true, false);
    }

    @Override
    public void run(MessageCreateEvent listener) {
        String[] args = this.params;
        Player plr = Groups.player.find(p -> p.plainName().contains(args[0]));
        if (plr == null) {listener.getChannel().sendMessage("Could not find that player!"); return;}
        if (plr.admin) {listener.getChannel().sendMessage("This player is already admin!"); return;}
        netServer.admins.adminPlayer(plr.uuid(), plr.usid());
        plr.admin = true;
        listener.getChannel().sendMessage("Admins powers has been added to " + plr.plainName());
    }
}
