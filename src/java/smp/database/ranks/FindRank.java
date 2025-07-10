package smp.database.ranks;

import com.mongodb.client.model.ReplaceOptions;
import smp.models.Rank;

import static com.mongodb.client.model.Filters.eq;
import static smp.database.DatabaseSystem.rankCollection;

public class FindRank {
    public static Rank findRank(String id){
        return rankCollection.find(eq("rankId", id)).first();
    }

    public static void updateRank(Rank rank){
        rankCollection.replaceOne(eq("rankId", rank.rankId), rank, new ReplaceOptions().upsert(true));
    }
}
