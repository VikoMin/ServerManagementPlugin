package smp.events;

import arc.util.Timer;
import mindustry.gen.Call;
import smp.vars.Variables;

import java.util.Objects;

import static smp.Utilities.exit;

public class GameOverEvent {
    public static void gameOverEvent(){
        Timer timer = new Timer();
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                if (Objects.equals(Variables.type, "hex")){
                    Call.infoMessage("[red]Hex round is over, the server will be restarted!");
                    exit();
                }
            }
        }, 15);
    }
}
