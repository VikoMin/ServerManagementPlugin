package smp.other;

import arc.Events;
import arc.util.Log;
import arc.util.Strings;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import mindustry.game.EventType.AdminRequestEvent;
import mindustry.game.Team;
import mindustry.gen.AdminRequestCallPacket;
import mindustry.gen.Call;
import mindustry.gen.Player;
import mindustry.net.Administration.TraceInfo;
import mindustry.net.Packets.KickReason;
import org.bson.Document;
import org.bson.conversions.Bson;
import smp.database.FindPlayerData;
import smp.menus.TextInput;
import smp.menus.TextInputs.BanTextInput;
import smp.menus.TextInputs.ReasonBanTextInput;

import java.time.Duration;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static mindustry.Vars.logic;
import static mindustry.Vars.net;
import static smp.database.FindPlayerData.getPlayerData;

public class BanMenu {

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
}