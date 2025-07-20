package smp;

import arc.Events;
import arc.util.CommandHandler;
import arc.util.Timer;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.gen.Player;
import mindustry.mod.*;
import mindustry.net.Packets;
import smp.commandSystem.CommandRegister;
import smp.commandSystem.mindustry.MindustryCommand;
import smp.system.config.ConfigSystem;

import java.util.List;

import static mindustry.Vars.netServer;
import static mindustry.Vars.player;
import static smp.antigrief.NodeGriefingWarning.initializeNodeGriefingWarnings;
import static smp.commandSystem.CommandRegister.getCommandsFromPackage;
import static smp.database.players.PlayerChecks.*;
import static smp.discord.Bot.initBot;
import static smp.database.DatabaseSystem.initDatabase;
import static smp.events.GameOverEvent.gameOverEvent;
import static smp.events.PlayerJoinEvent.joinEvent;
import static smp.events.PlayerLeaveEvent.leaveEvent;
import static smp.events.SendMessageEvent.rtvEvent;
import static smp.functions.Checks.kickIfBanned;
import static smp.history.History.loadHistory;
import static smp.history.History.loadRevert;
import static smp.other.BanSystem.loadBanMenu;
import static smp.other.InitializeRanks.initializeRanks;
import static smp.system.config.ConfigSystem.registerConfig;
import static smp.system.config.ConfigSystem.updateVariables;
import static smp.vars.Variables.*;

public class Main extends Plugin{
    public static CommandRegister<MindustryCommand<Player>> register;

    //called when game initializes
    @Override
    public void init() {
        registerConfig(Vars.dataDirectory.absolutePath(),  "config.json");
        updateVariables();
        initDatabase();
        loadHistory();
        loadRevert();
        if (betterBansEnabled) {
            loadBanMenu();
        }
        if (discordEnabled) {
            initBot();
        }
        if (griefingWarningsEnabled) {
            initializeNodeGriefingWarnings();
        }
        if (ranksEnabled) {
            initializeRanks();
        }
        if (timeCounterEnabled) {
            initializeCounter();
        }
        Vars.net.handleServer(Packets.Connect.class, (con, connect) -> {
            Events.fire(new EventType.ConnectionEvent(con));
            CheckPlayerIP(con);

            if (netServer.admins.isIPBanned(connect.addressTCP) || netServer.admins.isSubnetBanned(connect.addressTCP)){
                con.kick(Packets.KickReason.banned);
            }
            kickIfBanned(con);
        });
        Events.on(EventType.PlayerJoin.class, plr -> {
            joinEvent(plr.player);
        });
        Events.on(EventType.PlayerLeave.class, playerLeave -> {
            leaveEvent(playerLeave.player);
        });
        Events.on(EventType.GameOverEvent.class, playerLeave -> {
            gameOverEvent();
        });
        Events.on(EventType.TextInputEvent.class, textInputEvent -> {
            rtvEvent(textInputEvent.text, player);
        });
    }

    @Override
    //register commands that run on the server
    public void registerServerCommands(CommandHandler handler){

    }

    @Override
    //register commands that player can invoke in-game
    public void registerClientCommands(CommandHandler handler){

        register = new CommandRegister<>(handler);
        register.registerCommands((List<MindustryCommand<Player>>) getCommandsFromPackage("smp.commandSystem.commands.mindustry"));
        Timer.schedule(() -> {
            register.updateCommands();
        }, 5);
    }
}
