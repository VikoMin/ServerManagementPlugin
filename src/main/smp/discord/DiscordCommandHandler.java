package smp.discord;

import org.javacord.api.event.message.MessageCreateEvent;
import smp.commandSystem.discord.DiscordCommand;

import java.util.Objects;

import static smp.commandSystem.CommandRegister.discordCommands;
import static smp.discord.DiscordChecks.isConsole;
import static smp.discord.DiscordChecks.isModerator;
import static smp.Utilities.joinArrayString;

public class DiscordCommandHandler {
    public static String prefix = "()";

    public static void handleMessage(MessageCreateEvent listener){
        if (!listener.getMessageContent().isEmpty()) {
            String[] sepArray = listener.getMessageContent().split(" ");
            String[] newArray = sepArray;

            String name = sepArray[0];
            if (sepArray.length == 1) {
                newArray = new String[]{};
            } else {
                System.arraycopy(sepArray, 1, newArray, 0, sepArray.length - 1);
            }
            for (DiscordCommand command : discordCommands) {
                if (Objects.equals(name, prefix + command.name) && !listener.getMessageAuthor().isBotUser()) {
                    if (command.isAdmin && !isModerator(listener)) {
                        listener.getChannel().sendMessage("You need the game administrator permissions to perform that action!");
                        return;
                    }
                    if (command.isConsole && !isConsole(listener)) {
                        listener.getChannel().sendMessage("You need the game console permissions to perform that action!");
                        return;
                    }
                    if (command.argCount > newArray.length) {
                        listener.getChannel().sendMessage("Not enough arguments!");
                        return;
                    }
                    command.cmdUser = listener.getMessage().getUserAuthor().get();
                    if (sepArray.length != 1) {
                        String argsAfter = joinArrayString(newArray, command.argCount - 1);
                        newArray[command.argCount - 1] = argsAfter;
                    }
                    command.params = newArray;
                    command.run(listener);
                    return;
                }
            }
        }
    }
}
