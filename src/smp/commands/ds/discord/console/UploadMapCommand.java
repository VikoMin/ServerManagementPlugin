package smp.commands.ds.discord.console;

import mindustry.Vars;
import org.javacord.api.entity.message.MessageAttachment;
import org.javacord.api.event.message.MessageCreateEvent;
import smp.commands.ds.discord.DiscordCommand;
import smp.models.Rank;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.util.concurrent.ExecutionException;

import static arc.util.Strings.canParseInt;
import static java.lang.Integer.parseInt;
import static smp.database.InitializeDatabase.rankCollection;
import static smp.other.InitializeRanks.ranks;

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
