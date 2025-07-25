package smp.vars;

import arc.struct.ObjectMap;
import arc.struct.Seq;
import arc.util.Nullable;
import arc.util.Timekeeper;
import mindustry.core.NetServer;
import mindustry.gen.Groups;
import mindustry.gen.Player;
import mindustry.net.Administration;
import smp.commandSystem.commands.mindustry.basic.VotekickCommand;

import java.util.concurrent.atomic.AtomicInteger;

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

    /** RTV SYSTEM */
    public static AtomicInteger votes = new AtomicInteger(0);
    public static Seq<Player> votedPlayer = new Seq<>();
    public static boolean isVoting = false;

    public static Administration admins = new Administration();
    public static NetServer.ChatFormatter chatFormatter = (player, message) -> player == null ?
            message : "[coral][[" + player.coloredName() + "[coral]]:[white] " + message;

    /** DISCORD SYSTEM */
    public static long mindustryModeratorID;

    public static long mindustryConsoleID;

    public static long messageLogChannelID;

    public static long messageBanLogChannelID;

    public static String discordURL;

    public static String discordPrefix;

    public static String discordToken;

    public static boolean discordEnabled = true;

    public static boolean allDiscordCommandsEnabled = true;

    /** GLOBAL */

    public static String loc = "en";
    public static boolean playerBasedLoc = true;
    public static String mongoDbIP = "mongodb://localhost:27017/";
    public static String type = "default";

    /** MINDUSTRY */

    public static boolean ranksEnabled = true;
    public static boolean allCommandsEnabled = true;
    public static boolean griefingWarningsEnabled = true;
    public static boolean betterBansEnabled = true;
    public static boolean timeCounterEnabled = true;

    public static boolean welcomeMenuEnabled = true;
    public static String welcomeMenuText;
    public static String welcomeMenuTitle;

}
