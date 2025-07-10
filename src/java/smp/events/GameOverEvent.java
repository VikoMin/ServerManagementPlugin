package smp.events;

import arc.util.Timer;
import mindustry.gen.Call;

import java.util.Objects;

import static smp.Variables.currentSetting;
import static smp.functions.Utilities.exit;

public class GameOverEvent {
    public static void gameOverEvent(){
        Timer timer = new Timer();
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                if (Objects.equals(currentSetting.type, "hex")){
                    Call.infoMessage("[red]Hex round is over, the server will be restarted!");
                    exit();
                }
            }
        }, 15);
    }
}
