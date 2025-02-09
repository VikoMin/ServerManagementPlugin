package smp.commands.discord;

import arc.struct.OrderedSet;
import arc.struct.Seq;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import smp.commands.CommandParameter;
import smp.errors.MindustryErrors;

import java.io.IOException;

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
