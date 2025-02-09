package smp.menus;

import mindustry.gen.Call;
import mindustry.gen.Player;
import mindustry.net.NetConnection;

public class TextInput {

    public final String name;
    public final String text;
    public final int textLength;
    public final String defaultText;
    public final boolean numeric;
    public final int listener;

    public TextInput(String name, String text, int textLength, String defaultText, boolean numeric, int listener) {
        this.name = name;
        this.text = text;
        this.textLength = textLength;
        this.defaultText = defaultText;
        this.numeric = numeric;
        this.listener = listener;
    }

    public void run(NetConnection connection){
        Call.textInput(connection, listener, name, text, textLength, defaultText, numeric);
    }
}
