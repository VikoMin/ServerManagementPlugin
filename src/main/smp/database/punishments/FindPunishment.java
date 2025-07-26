package smp.database.punishments;

import smp.models.PlayerData;
import smp.models.Punishment;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

public class FindPunishment {
    public static Punishment findPunishmentLastBan(PlayerData data){
        ArrayList<Punishment> punishments = data.punishments;
        AtomicLong maxValue = new AtomicLong(0L);
        punishments.forEach(punishment -> {
            if (Objects.equals(punishment.punishmentType, "ban") && punishment.punishmentDuration > maxValue.get()){
                maxValue.set(punishment.punishmentDuration);
            }
        });
        return punishments.stream().filter(p -> p.punishmentDuration == maxValue.get()).findFirst().orElse(null);
    }
}
