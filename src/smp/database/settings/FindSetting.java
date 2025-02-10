package smp.database.settings;

import arc.Core;
import com.mongodb.client.model.ReplaceOptions;
import smp.models.Setting;

import java.net.UnknownHostException;
import java.util.Optional;

import static com.mongodb.client.model.Filters.eq;
import static smp.database.InitializeDatabase.settingCollection;

public class FindSetting {

    public static Setting findSettingOrCreate() throws UnknownHostException {
        return Optional.ofNullable(settingCollection.find(eq("port", Core.settings.getInt("port"))).first()).orElse(
                new Setting()
        );
    }

    public static Setting findSetting(int port){
        return settingCollection.find(eq("port", port)).first();
    }

    public static void updateSetting(Setting setting){
        settingCollection.replaceOne(eq("port", setting.port), setting, new ReplaceOptions().upsert(true));
    }
}
