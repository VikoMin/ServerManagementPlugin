package smp.database;

import arc.Core;
import mindustry.Vars;
import mindustry.gen.Player;
import smp.models.PlayerData;
import smp.models.Setting;

import java.net.UnknownHostException;
import java.util.Optional;

import static com.mongodb.client.model.Filters.eq;
import static smp.database.InitializeDatabase.collection;
import static smp.database.InitializeDatabase.settingCollection;

public class FindSetting {

    public static Setting findSettingOrCreate() throws UnknownHostException {
        return Optional.ofNullable(settingCollection.find(eq("port", Core.settings.getInt("port"))).first()).orElse(
                new Setting()
        );
    }

    public static Setting findSetting(int port){

        return null;
    }
}
