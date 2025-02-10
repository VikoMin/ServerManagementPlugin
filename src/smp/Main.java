package smp;

import arc.Events;
import arc.util.CommandHandler;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.mod.*;
import mindustry.net.Packets;
import smp.commands.CommandRegister;
import smp.commands.admin.AdminChatCommand;
import smp.commands.admin.RevertCommand;
import smp.commands.basic.*;
import smp.database.players.Counter;

import java.net.UnknownHostException;

import static mindustry.Vars.netServer;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import static smp.antigrief.NodeGriefingWarning.initializeNodeGriefingWarnings;
import static smp.database.players.PlayerChecks.*;
import static smp.discord.Bot.initBot;
import static smp.database.InitializeDatabase.initDatabase;
import static smp.events.PlayerJoinEvent.joinEvent;
import static smp.events.PlayerLeaveEvent.leaveEvent;
import static smp.functions.Checks.kickIfBanned;
import static smp.history.History.loadHistory;
import static smp.history.History.loadRevert;
import static smp.other.BanMenu.loadBanMenu;
import static smp.other.InitializeRanks.initializeRanks;
import static smp.other.InitializeSettings.initializeSettings;

public class Main extends Plugin{
    public static CommandRegister register;

    //called when game initializes
    @Override
    public void init() {
        initDatabase();
        loadHistory();
        loadRevert();
        loadBanMenu();
        initBot();
        initializeNodeGriefingWarnings();
        initializeRanks();
        Counter.initializeCounter();
        MongoDbCheck();
        try {
            initializeSettings();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        Events.on(EventType.PlayerJoin.class, plr -> {
            joinEvent(plr.player);
        });
        Events.on(EventType.PlayerLeave.class, playerLeave -> {
            leaveEvent(playerLeave.player);
        });
        Vars.net.handleServer(Packets.Connect.class, (con, connect) -> {
            Events.fire(new EventType.ConnectionEvent(con));
            MongoDbPlayerIpCheck(con);

            if (netServer.admins.isIPBanned(connect.addressTCP) || netServer.admins.isSubnetBanned(connect.addressTCP)){
                con.kick(Packets.KickReason.banned);
            }
            kickIfBanned(con);
        });
    }

    @Override
    //register commands that run on the server
    public void registerServerCommands(CommandHandler handler){

    }

    @Override
    //register commands that player can invoke in-game
    public void registerClientCommands(CommandHandler handler){
        TestCommand testCommand = new TestCommand();
        HelpCommand helpCommand = new HelpCommand();
        WhisperCommand whisperCommand = new WhisperCommand();
        SyncCommand syncCommand = new SyncCommand();
        TeamChatCommand teamChatCommand = new TeamChatCommand();
        VotekickCommand votekickCommand = new VotekickCommand();
        VoteCommand voteCommand = new VoteCommand();
        HistoryCommand historyCommand = new HistoryCommand();
        StatsCommand statsCommands = new StatsCommand();
        ServerHopCommand serverHopCommand = new ServerHopCommand();
        register = new CommandRegister(handler);
        register.updateCommands();
        register.registerCommand(testCommand);
        register.registerCommand(helpCommand);
        register.registerCommand(whisperCommand);
        register.registerCommand(syncCommand);
        register.registerCommand(teamChatCommand);
        register.registerCommand(votekickCommand);
        register.registerCommand(voteCommand,
                historyCommand,
                statsCommands,
                serverHopCommand);

        /* admin commands */

        AdminChatCommand adminChatCommand = new AdminChatCommand();
//        OverwriteCommand overwriteCommand = new OverwriteCommand();
        RevertCommand revertCommand = new RevertCommand();
        register.registerCommand(adminChatCommand, revertCommand);
    }
}
