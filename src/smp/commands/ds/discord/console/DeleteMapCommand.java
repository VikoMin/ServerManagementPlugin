package smp.commands.ds.discord.console;

import mindustry.Vars;
import org.javacord.api.entity.message.MessageAttachment;
import org.javacord.api.event.message.MessageCreateEvent;
import smp.commands.ds.discord.DiscordCommand;

import java.io.File;

public class DeleteMapCommand extends DiscordCommand {
    public DeleteMapCommand() {
        super("deletemap", " <mapname> -> deletes map from the server.", 1,false, true);
    }

    @Override
    public void run(MessageCreateEvent listener) {
        String[] args = this.params;
        File file = Vars.customMapDirectory.child(args[0]).file();
        if (file.exists()){
            file.delete();
            listener.getChannel().sendMessage("Map has been deleted!");
        } else {
            listener.getChannel().sendMessage("Map is not found!");
        }
    }
}
