package smp.commandSystem;

public class CommandParameter {
    public final String name, rawname;
    public final boolean optional, variable;

    public CommandParameter(String name) {
        this.name = name.substring(1, name.length() - 1);
        this.rawname = name;

        this.optional = name.startsWith("[") && name.endsWith("]");
        this.variable = name.contains("...");
    }
}
