package smp.database.ranks;

import smp.models.Rank;
import smp.models.Setting;

import static com.mongodb.client.model.Filters.eq;
import static smp.database.InitializeDatabase.rankCollection;
import static smp.database.InitializeDatabase.settingCollection;

public class FindRank {
    public static Rank findRank(String id){
        return rankCollection.find(eq("rankId", id)).first();
    }
}
