package smp.other;

import arc.struct.Seq;
import arc.util.Log;
import com.mongodb.client.MongoCursor;
import smp.models.Rank;

import static smp.database.DatabaseSystem.rankCollection;

public class InitializeRanks {
    public static Seq<Rank> ranks = new Seq<>();

    public static void initializeRanks (){
        ranks.clear();
        try (MongoCursor<Rank> cursor = rankCollection.find().iterator()) {
            while (cursor.hasNext()) {
                Rank rank = cursor.next();
                Log.debug("Rank has been added: " + rank.name);
                ranks.add(rank);
            }
        }
    }
}