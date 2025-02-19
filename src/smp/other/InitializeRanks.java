package smp.other;

import arc.struct.Seq;
import arc.util.Log;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import smp.models.PlayerData;
import smp.models.Rank;

import java.util.ArrayList;

import static smp.database.InitializeDatabase.collection;
import static smp.database.InitializeDatabase.rankCollection;

public class InitializeRanks {
    public static Seq<Rank> ranks = new Seq<>();

    public static void initializeRanks (){
        ranks.clear();
        try (MongoCursor<Rank> cursor = rankCollection.find().iterator()) {
            while (cursor.hasNext()) {
                Rank rank = cursor.next();
                Log.debug("Rank has been added: " + rank.rankName);
                ranks.add(rank);
            }
        }
    }
}