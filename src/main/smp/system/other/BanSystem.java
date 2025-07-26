package smp.system.other;

import arc.Events;
import arc.util.Log;
import mindustry.game.EventType.AdminRequestEvent;
import mindustry.game.Team;
import mindustry.gen.AdminRequestCallPacket;
import mindustry.gen.Call;
import mindustry.gen.Player;
import mindustry.net.Administration.TraceInfo;
import mindustry.net.Packets.KickReason;
import smp.menus.TextInputs.ReasonBanTextInput;
import smp.models.PlayerData;
import smp.models.Punishment;

import java.util.Date;

import static mindustry.Vars.logic;
import static mindustry.Vars.net;
import static smp.discord.Bot.banLogChannel;
import static smp.vars.Variables.discordEnabled;
import static smp.vars.Variables.discordURL;
import static smp.database.DatabaseSystem.updateDatabaseDocument;
import static smp.database.DatabaseSystem.playerCollection;
import static smp.database.players.FindPlayerData.getPlayerData;
import static smp.discord.Bot.messageLogChannel;
import static smp.discord.embedSystem.embeds.BanEmbed.banEmbed;
import static smp.functions.FindPlayer.findPlayerByName;
import static smp.functions.Wrappers.timeToDuration;

public class BanSystem {

    public static void loadBanMenu() {
        net.handleServer(AdminRequestCallPacket.class, (con, packet) -> {
            var player = con.player;
            var other = packet.other;

            if (!player.admin || other == null || (other.admin && other != player)) {
                return;
            }

            var action = packet.action;
            var params = packet.params;

            Events.fire(new AdminRequestEvent(player, other, action));

            switch (action) {
                case wave -> {
                    logic.skipWave();
                    Log.info("&lc@ &fi&lk[&lb@&fi&lk]&fb has skipped the wave.", player.plainName(), player.uuid());
                }

                case ban -> {
                    ReasonBanTextInput reasonBanTextInput = new ReasonBanTextInput(getPlayerData(other.uuid()));
                    reasonBanTextInput.run(player.con);
                }

                case kick -> {
                    other.kick(KickReason.kick);
                    Log.info("&lc@ &fi&lk[&lb@&fi&lk]&fb has kicked @ &fi&lk[&lb@&fi&lk]&fb.", player.plainName(), player.uuid(), other.plainName(), other.uuid());
                }

                case trace -> {
                    Call.traceInfo(player.con, other, new TraceInfo(
                            other.ip(),
                            other.uuid(),
                            other.locale,
                            other.con.modclient,
                            other.con.mobile,
                            other.getInfo().timesJoined,
                            other.getInfo().timesKicked,
                            other.getInfo().ips.toArray(String.class),
                            other.getInfo().names.toArray(String.class)
                    ));

                    Log.info("&lc@ &fi&lk[&lb@&fi&lk]&fb has requested trace info of @ &fi&lk[&lb@&fi&lk]&fb.", player.plainName(), player.uuid(), other.plainName(), other.uuid());
                }

                case switchTeam -> {
                    if (params instanceof Team team)
                        other.team(team);
                }
            }
        });
    }

    public static void banPlayer(Date date, String reason, PlayerData data, Player moderator){
        Punishment punishment = new Punishment("ban", date.getTime(), reason, moderator.plainName());
        data.punishments.add(punishment);
        Player plr = findPlayerByName(data.name);
        if (plr != null){
            Call.sendMessage(plr.plainName() + " has been banned for: " + reason);
            plr.con.kick("[red]You have been banned!\n\n" + "[white]Reason: " + reason +"\nDuration: " + timeToDuration(date.getTime()) + " until unban\nIf you think this is a mistake, make sure to appeal ban in our discord: " + discordURL, 0);
        }
        updateDatabaseDocument(data, playerCollection, "_id", data.id);
        if (discordEnabled) {
            banLogChannel.sendMessage(banEmbed(data, reason, date.getTime(), moderator.plainName()));
        }
    }
}