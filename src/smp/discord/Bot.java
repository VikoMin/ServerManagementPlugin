package smp.discord;

import arc.Events;
import arc.util.Timer;
import mindustry.game.EventType;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.intent.Intent;
import smp.Variables;
import smp.commands.discord.*;
import smp.models.PlayerData;

import static smp.commands.CommandRegister.registerDiscordCommand;
import static smp.database.FindPlayerData.getPlayerData;

public class Bot {
    public static TextChannel messageLogChannel;
    public static void initBot(){
        DiscordApi api = new DiscordApiBuilder()
                .setToken("MTE4OTYxNDc5OTU2NzQ1NDIzMA.GOp9iQ.DD_ajMO_tKjRyoQjiUnLRBnx655Gs0dOTc3uUU")
                .addIntents(Intent.GUILDS, Intent.MESSAGE_CONTENT, Intent.GUILD_MESSAGES)
                .login()
                .join();
        api.addMessageCreateListener(DiscordCommandHandler::handleMessage);
        messageLogChannel = api.getTextChannelById(Variables.messageLogChannelID).get();
        ListPlayersCommand listPlayersCommand = new ListPlayersCommand();
        AdminAddCommand adminAddCommand = new AdminAddCommand();
        AdminRewCommand adminRewCommand = new AdminRewCommand();
        HelpCommand helpCommand = new HelpCommand();
        SetRankCommand setRankCommand = new SetRankCommand();
        registerDiscordCommand(listPlayersCommand);
        registerDiscordCommand(adminAddCommand);
        registerDiscordCommand(adminRewCommand);
        registerDiscordCommand(helpCommand);
        registerDiscordCommand(setRankCommand);

        Events.on(EventType.PlayerChatEvent.class, event  -> {
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
                    messageLogChannel.sendMessage("`" + event.player.plainName()  + " ("+ user.id + ")" +" left the server!" + "`");
                }, 0.2f));
    }
}
