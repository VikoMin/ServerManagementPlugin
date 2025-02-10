package smp.commands.basic;

import com.mongodb.client.MongoCursor;
import mindustry.gen.Call;
import mindustry.gen.Player;
import smp.commands.BasicCommand;
import smp.errors.MindustryErrors;
import smp.models.Setting;

import java.net.UnknownHostException;

import static arc.util.Strings.canParseInt;
import static smp.database.settings.FindSetting.findSetting;
import static smp.database.InitializeDatabase.settingCollection;

public class ServerHopCommand extends BasicCommand<Player> {
    public ServerHopCommand() {
        super("serverhop", "Connects you to the other server in this network by port.", new arc.struct.Seq<String>().add("[port]"));
    }

    @Override
    public void run(String[] args, Player parameter) throws MindustryErrors.CommandException {
        if (args[0].isEmpty()){
            StringBuilder builder = new StringBuilder();

            try (MongoCursor<Setting> cursor = settingCollection.find().iterator()) {
                while (cursor.hasNext()) {
                    Setting set = new Setting();
                    builder
                            .append("[white]Server: ")
                            .append(set.serverName)
                            .append(" [white]port: ")
                            .append(set.port)
                            .append("\n");
                }
            } catch (UnknownHostException e) {
                throw new RuntimeException(e);
            }

            parameter.sendMessage(builder.toString());
            return;
        }

        if (!canParseInt(args[0])) throw new MindustryErrors.CommandException("integer.parse.fail", parameter);

        Setting setting = findSetting(Integer.parseInt(args[0]));

        if (setting == null){parameter.sendMessage("[red]Could not find that server!"); return;}

        Call.connect(parameter.con, setting.ip, setting.port);
    }
}
