package smp.commands.mindustry.basic;

import arc.struct.ObjectIntMap;
import arc.util.Strings;
import arc.util.Timekeeper;
import arc.util.Timer;
import mindustry.gen.Call;
import mindustry.gen.Groups;
import mindustry.gen.Player;
import mindustry.net.Administration;
import mindustry.net.Packets;
import smp.commands.BasicCommand;

import static smp.Variables.*;
import static smp.functions.FindPlayer.findPlayerByName;

public class VotekickCommand extends BasicCommand<Player> {


    public class VoteSession{
        Player target;
        ObjectIntMap<String> voted = new ObjectIntMap<>();
        Timer.Task task;
        int votes;

        public VoteSession(Player target){
            this.target = target;
            this.task = Timer.schedule(() -> {
                if(!checkPass()){
                    Call.sendMessage(Strings.format("[lightgray]Vote failed. Not enough votes to kick[orange] @[lightgray].", target.name));
                    currentlyKicking = null;
                    task.cancel();
                }
            }, voteDuration);
        }

        void vote(Player player, int d){
            int lastVote = voted.get(player.uuid(), 0) | voted.get(admins.getInfo(player.uuid()).lastIP, 0);
            votes -= lastVote;

            votes += d;
            voted.put(player.uuid(), d);
            voted.put(admins.getInfo(player.uuid()).lastIP, d);

            Call.sendMessage(Strings.format("[lightgray]@[lightgray] has voted on kicking[orange] @[lightgray].[accent] (@/@)\n[lightgray]Type[orange] /vote <y/n>[] to agree.",
                    player.name, target.name, votes, votesRequired()));

            checkPass();
        }

        boolean checkPass(){
            if(votes >= votesRequired()){
                Call.sendMessage(Strings.format("[orange]Vote passed.[scarlet] @[orange] will be banned from the server for @ minutes.", target.name, (kickDuration / 60)));
                Groups.player.each(p -> p.uuid().equals(target.uuid()), p -> p.kick(Packets.KickReason.vote, kickDuration * 1000));
                currentlyKicking = null;
                task.cancel();
                return true;
            }
            return false;
        }
    }

    public VotekickCommand() {
        super("votekick", "Vote to kick a player with a valid reason.", new arc.struct.Seq<String>().add("<player>").add("<reason...>"));
    }

    @Override
    public void run(String[] args, Player player) {
        if(!Administration.Config.enableVotekick.bool()){
            player.sendMessage("[scarlet]Vote-kick is disabled on this server.");
            return;
        }

        if(Groups.player.size() < 3){
            player.sendMessage("[scarlet]At least 3 players are needed to start a votekick.");
            return;
        }
        if(currentlyKicking != null){
            player.sendMessage("[scarlet]A vote is already in progress.");
            return;
        }
        Player found;
        if(args[0].length() > 1 && args[0].startsWith("#") && Strings.canParseInt(args[0].substring(1))){
            int id = Strings.parseInt(args[0].substring(1));
            found = Groups.player.find(p -> p.id() == id);
        }else{
            found = findPlayerByName(args[0]);
        }

        if(found != null){
            if(found == player){
                player.sendMessage("[scarlet]You can't vote to kick yourself.");
            }else if(found.admin){
                player.sendMessage("[scarlet]Did you really expect to be able to kick an admin?");
            }else if(found.team() != player.team()){
                player.sendMessage("[scarlet]Only players on your team can be kicked.");
            }else{
                Timekeeper vtime = cooldowns.get(player.uuid(), () -> new Timekeeper(voteCooldown));

                if(!vtime.get()){
                    player.sendMessage("[scarlet]You must wait " + voteCooldown/60 + " minutes between votekicks.");
                    return;
                }

                VoteSession session = new VoteSession(found);
                session.vote(player, 1);
                Call.sendMessage(Strings.format("[lightgray]Reason:[orange] @[lightgray].", args[1]));
                vtime.reset();
                currentlyKicking = session;
            }
        }else{
            player.sendMessage("[scarlet]No player [orange]'" + args[0] + "'[scarlet] found.");
        }
    }
}
