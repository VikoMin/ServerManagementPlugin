package smp.functions;

import smp.models.PlayerData;
import smp.models.Rank;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static arc.util.Strings.canParseInt;
import static arc.util.Strings.parseInt;
import static smp.database.DatabaseSystem.findDatabaseDocument;
import static smp.database.DatabaseSystem.rankCollection;
import static smp.Utilities.createHashMap;

public class Wrappers {
    public static String statsWrapper(PlayerData data){
        Rank rank = findDatabaseDocument(rankCollection, createHashMap("id", data.rank));;
       StringBuilder out = new StringBuilder();
       out.append("[orange]Player name: ").append(data.name).append("\n");
       if (rank != null) {
           out.append("[orange]Rank: ").append(rank.name).append("\n");
       } else {
           out.append("[orange]Rank: ").append("None").append("\n");
       }
       out.append("[orange]Playtime: ").append(data.playtime).append(" minutes").append("\n");
       return out.toString();
    };

    public static Date formatBanTime(String time){
        Date date = new Date();
        if (time.toCharArray()[time.length()-1] == 'h'){
            String formattedTime = time.substring(0, time.length()-1);
            if (canParseInt(formattedTime)){
                return new Date(date.getTime() + TimeUnit.HOURS.toMillis(parseInt(formattedTime)));
            }
        }
        else if (time.toCharArray()[time.length()-1] == 'd'){
            String formattedTime = time.substring(0, time.length()-1);
            if (canParseInt(formattedTime)){
                return new Date(date.getTime() + TimeUnit.DAYS.toMillis(parseInt(formattedTime)));
            }
        }
        else if (time.equals("p")){
            return new Date(date.getTime() + TimeUnit.DAYS.toMillis(180));
        }
        else {
            if (canParseInt(time)){
                return new Date(date.getTime() + TimeUnit.DAYS.toMillis(parseInt(time)));
            }
        }
        return null;
    }

    /*public static String dateToDuration(Date date){
        Date newDate = new Date();
        newDate.setTime(date.getTime()-newDate.getTime());
        return null;
    }*/
    public static String timeToDuration(long time){
        Date date = new Date();
        Date newDate = new Date(time-date.getTime());
        long newTime = time - date.getTime();
        int days = (int) Math.floor((double) newTime /86400000);
        int hours =  newDate.getHours();
        int minutes = newDate.getMinutes();
        return days + " days " + hours + " hours and " + minutes + " minutes" ;
    }
    public static String minutesFormat(long minutes){
        int days = (int) Math.floor((double) minutes / 1440);
        int hours = (int) Math.floor((double) (minutes - days* 1440L)/60);
        int eMinutes = (int) Math.floor((minutes-days* 1440L));
        return days + " days " + hours + " hours and " + eMinutes + " minutes" ;
    }
}
