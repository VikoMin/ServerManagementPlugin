package smp.commandSystem.commands.mindustry.admin;

import arc.struct.Seq;
import mindustry.gen.Groups;
import mindustry.gen.Player;
import mindustry.graphics.Pal;
import smp.commandSystem.mindustry.BasicAdminCommand;

import static smp.Variables.chatFormatter;

public class AdminChatCommand extends BasicAdminCommand {
    public AdminChatCommand() {
        super("a", "Sends message to admin chat", new Seq<String>().add("<message...>"), true);
    }

    @Override
    public void run(String[] args, Player player) {
        String raw = "[#" + Pal.adminChat.toString() + "]<A> " + chatFormatter.format(player, args[0]);
        Groups.player.each(Player::admin, a -> a.sendMessage(raw, player, args[0]));
    }

}
