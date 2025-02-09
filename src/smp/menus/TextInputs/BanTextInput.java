package smp.menus.TextInputs;

import mindustry.gen.Player;
import smp.menus.TextInput;
import smp.models.PlayerData;

import java.util.Objects;

import static smp.functions.Utilities.banPlayer;
import static smp.functions.Wrappers.formatBanTime;

public class BanTextInput extends TextInput {

    public BanTextInput(String reason, PlayerData target) {
        super(
                "Ban",
                "Time of ban (H/D/P)?",
                7,
                "",
                false,
                mindustry.ui.Menus.registerTextInput((player, option) -> {
                    banPlayer(Objects.requireNonNull(formatBanTime(option)), reason, target, player);
                })
        );
    }
}
