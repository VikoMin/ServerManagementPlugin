package smp;

import arc.struct.ObjectMap;
import arc.util.Nullable;
import arc.util.Timekeeper;
import mindustry.core.NetServer;
import mindustry.gen.Groups;
import mindustry.net.Administration;
import smp.commands.basic.VotekickCommand;

public class Variables {

    /** VOTEKICKS */
    public static @Nullable VotekickCommand.VoteSession currentlyKicking = null;
    /** Duration of a kick in seconds. */
    public static int kickDuration = 60 * 60;
    /** Voting round duration in seconds. */
    public static float voteDuration = 0.5f * 60;
    /** Cooldown between votes in seconds. */
    public static int voteCooldown = 60 * 5;

    public static ObjectMap<String, Timekeeper> cooldowns = new ObjectMap<>();

    public static int votesRequired(){
        return 2 + (Groups.player.size() > 4 ? 1 : 0);
    }
    /** VOTEKICK DONE */

    public static Administration admins = new Administration();

    public static NetServer.ChatFormatter chatFormatter = (player, message) -> player == null ? message : "[coral][[" + player.coloredName() + "[coral]]:[white] " + message;

    public static long mindustryModeratorID = 1127228853505507359L;

    public static long mindustryConsoleID = 1127228950633005067L;

    public static long messageLogChannelID = 1134453759959896096L;

    public static String discordURL;
}
