package smp.menus;

import mindustry.gen.Call;
import mindustry.net.NetConnection;

import static mindustry.ui.Menus.registerMenu;

public class Menu {
    public final String title;
    public final String text;
    public final String[][] buttons;
    public final int listener;


    public Menu(String title, String text, String[][] buttons, int listener) {
        this.title = title;
        this.text = text;
        this.buttons = buttons;
        this.listener = listener;
    }

    public void run(){
        Call.menu(listener, title, text, buttons);
    }

    public void run(NetConnection connect){
        Call.menu(connect, listener, title, text, buttons);
    }
}
