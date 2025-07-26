package smp.commandSystem.discord;

import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

public class DiscordCommand {

    public final String name, desc;
    public String[] params;
    public boolean isAdmin = false;
    public boolean isConsole = false;
    public int argCount = 0;
    public User cmdUser;

    public DiscordCommand(String name, String desc, int argCount) {
        this.name = name;
        this.desc = desc;
        this.argCount = argCount;
    }

    public DiscordCommand(String name, String desc, int argCount, boolean isAdmin, boolean isConsole) {
        this.name = name;
        this.desc = desc;
        this.argCount = argCount;
        this.isAdmin = isAdmin;
        this.isConsole = isConsole;
    }


    public void run(MessageCreateEvent listener) {

    }
}
