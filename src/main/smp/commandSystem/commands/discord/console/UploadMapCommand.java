package smp.commandSystem.commands.discord.console;

import mindustry.Vars;
import org.javacord.api.entity.message.MessageAttachment;
import org.javacord.api.event.message.MessageCreateEvent;
import smp.commandSystem.discord.DiscordCommand;

import static java.lang.Integer.parseInt;

public class UploadMapCommand extends DiscordCommand {
    public UploadMapCommand() {
        super("uploadmap", " <attachments...> -> Uploads map to the server.", 0,false, true);
    }

    @Override
    public void run(MessageCreateEvent listener) {
        for (MessageAttachment attachment : listener.getMessage().getAttachments()){
            try {
                var file = Vars.customMapDirectory.child(attachment.getFileName());
                file.writeBytes(attachment.asByteArray().get());
                listener.getChannel().sendMessage("Success!");
            } catch (Exception ignored){
                listener.getChannel().sendMessage("Something went wrong!");
            }
        }
    }
}
