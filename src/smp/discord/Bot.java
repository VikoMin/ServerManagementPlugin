package smp.discord;

import arc.Core;
import arc.Events;
import arc.util.Timer;
import mindustry.game.EventType;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.intent.Intent;
import org.javacord.api.event.message.MessageCreateEvent;
import smp.Variables;
import smp.commands.ds.discord.admin.AdminAddCommand;
import smp.commands.ds.discord.admin.AdminRewCommand;
import smp.commands.ds.discord.admin.BanCommand;
import smp.commands.ds.discord.admin.UnbanCommand;
import smp.commands.ds.discord.basic.*;
import smp.commands.ds.discord.console.CreateRankCommand;
import smp.commands.ds.discord.console.DeleteRankCommand;
import smp.commands.ds.discord.console.MagicStickCommand;
import smp.commands.ds.discord.console.SetRankCommand;
import smp.models.PlayerData;

import static smp.Variables.*;
import static smp.commands.CommandRegister.registerDiscordCommand;
import static smp.database.players.FindPlayerData.getPlayerData;
import static smp.functions.Utilities.makeCoreSettingLong;
import static smp.functions.Utilities.makeCoreSettingString;

public class Bot {
    public static TextChannel messageLogChannel;
    public static TextChannel banLogChannel;
    public static void initBot(){
        makeCoreSettingString("Looks like you don't have discord token registered: ",
                "discordToken");
        makeCoreSettingLong("Looks like you don't have message log channel ID registered: ",
                "messageLogChannelID");
        makeCoreSettingLong("Looks like you don't have ban log channel registered: ",
                "messageBanLogChannelID");
        makeCoreSettingLong("Looks like you don't have mindustry console role ID registered: ",
                "mindustryConsoleID");
        makeCoreSettingLong("Looks like you don't have mindustry moderator role ID registered: ",
                "mindustryModeratorID");
        discordToken = Core.settings.getString("discordToken");
        messageLogChannelID = Core.settings.getLong("messageLogChannelID");;
        messageBanLogChannelID = Core.settings.getLong("messageBanLogChannelID");;
        mindustryConsoleID = Core.settings.getLong("mindustryConsoleID");;
        mindustryModeratorID = Core.settings.getLong("mindustryModeratorID");;


        DiscordApi api = new DiscordApiBuilder()
                .setToken(discordToken)
                .addIntents(Intent.GUILDS, Intent.MESSAGE_CONTENT, Intent.GUILD_MESSAGES)
                .login()
                .join();
        api.addMessageCreateListener(DiscordCommandHandler::handleMessage);
        messageLogChannel = api.getTextChannelById(Variables.messageLogChannelID).get();
        banLogChannel = api.getTextChannelById(messageBanLogChannelID).get();

        ListPlayersCommand listPlayersCommand = new ListPlayersCommand();
        AdminAddCommand adminAddCommand = new AdminAddCommand();
        AdminRewCommand adminRewCommand = new AdminRewCommand();
        HelpCommand helpCommand = new HelpCommand();
        SetRankCommand setRankCommand = new SetRankCommand();
        BanCommand banCommand = new BanCommand();
        UnbanCommand unbanCommand = new UnbanCommand();
        CreateRankCommand createRankCommand = new CreateRankCommand();
        SearchCommand searchCommand = new SearchCommand();
        DeleteRankCommand deleteRankCommand = new DeleteRankCommand();
        ListRanksCommand listRanksCommand= new ListRanksCommand();
        MagicStickCommand magicStickCommand = new MagicStickCommand();
        LoginCommand loginCommand = new LoginCommand();
        registerDiscordCommand(listPlayersCommand,
                adminAddCommand,
                adminRewCommand,
                helpCommand,
                setRankCommand,
                banCommand,
                unbanCommand,
                createRankCommand,
                searchCommand,
                deleteRankCommand,
                listRanksCommand,
                magicStickCommand,
                loginCommand);
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

    public static void sendMessage(MessageCreateEvent listener, String message){
        if (listener.getMessage().isThreadMessage()){
            listener.getServerThreadChannel().get().sendMessage(String.valueOf(message));
        } else {
            listener.getChannel().sendMessage(String.valueOf(message));
        }
    }
}
