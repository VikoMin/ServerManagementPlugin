package smp.discord;

import arc.Events;
import arc.util.Timer;
import mindustry.game.EventType;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.intent.Intent;
import org.javacord.api.event.message.MessageCreateEvent;
import smp.vars.Variables;
import smp.commandSystem.discord.DiscordCommand;
import smp.models.PlayerData;

import java.util.List;

import static smp.vars.Variables.*;
import static smp.commandSystem.CommandRegister.getCommandsFromPackage;
import static smp.commandSystem.CommandRegister.registerDiscordCommands;
import static smp.database.players.FindPlayerData.getPlayerData;

public class Bot {
    public static TextChannel messageLogChannel;
    public static TextChannel banLogChannel;

    public static void initBot() {

        DiscordApi api = new DiscordApiBuilder()
                .setToken(discordToken)
                .addIntents(Intent.GUILDS, Intent.MESSAGE_CONTENT, Intent.GUILD_MESSAGES)
                .login()
                .join();
        api.addMessageCreateListener(DiscordCommandHandler::handleMessage);
        messageLogChannel = api.getTextChannelById(Variables.messageLogChannelID).get();
        banLogChannel = api.getTextChannelById(messageBanLogChannelID).get();

        registerDiscordCommands((List<DiscordCommand>) getCommandsFromPackage("smp.commandSystem.commands.discord"));
        if (messageLogChannel == null) return;
        Events.on(EventType.PlayerChatEvent.class, event -> {
            if (event.message.startsWith("/")) {
                return;
            }
            messageLogChannel.sendMessage("`" + event.player.plainName() + ": " + event.message + "`");
        });
        Events.on(EventType.PlayerJoin.class, event ->
                Timer.schedule(() -> {
                    PlayerData data = getPlayerData(event.player.uuid());
                    if (data != null) {
                        messageLogChannel.sendMessage("`" + event.player.plainName() + " (" + data.id + ")" + " joined the server!" + "`");
                    }
                }, 0.2f));
        Events.on(EventType.PlayerLeave.class, event ->
                Timer.schedule(() -> {
                    PlayerData user = getPlayerData(event.player.uuid());
                    messageLogChannel.sendMessage("`" + event.player.plainName() + " (" + user.id + ")" + " left the server!" + "`");
                }, 0.2f));
    }

    public static void sendMessage(MessageCreateEvent listener, String message) {
        if (listener.getMessage().isThreadMessage()) {
            listener.getServerThreadChannel().get().sendMessage(String.valueOf(message));
        } else {
            listener.getChannel().sendMessage(String.valueOf(message));
        }
    }
}
