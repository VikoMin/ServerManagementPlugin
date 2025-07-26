package smp.menus.TextInputs;

import smp.menus.TextInput;
import smp.models.PlayerData;

public class ReasonBanTextInput extends TextInput {

    public ReasonBanTextInput(PlayerData target) {
        super(
                "Ban",
                "Reason of ban?",
                256,
                "",
                false,
                mindustry.ui.Menus.registerTextInput((player, option) -> {
                    BanTextInput banTextInput = new BanTextInput(option, target);
                    banTextInput.run(player.con);
                })
        );
    }
}
