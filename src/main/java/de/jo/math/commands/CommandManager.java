package de.jo.math.commands;

import de.jo.util.PackageScanner;
import de.jo.util.Strings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Johannes Hans 27.10.2023
 * @Project ConsoleTools
 */
public class CommandManager {

    private List<Command> commands = new ArrayList<>();

    public CommandManager() {

        loadCommands("de.jo.math.commands.impl");
    }

    private void loadCommands(String pckg) {
        for(Object obj : PackageScanner.classes(pckg, CommandInfo.class)) {
            if(obj instanceof Class<?>) {
                Class<?> c = (Class<?>) obj;
                List<Class<?>> interfaces = Arrays.asList(c.getInterfaces());
                if(interfaces.size() == 0) return;
                if(interfaces.contains(Command.class)) {
                    try {
                        Command cmd = (Command) c.newInstance();
                        commands.add(cmd);
                    } catch(Exception ex) {
                        Strings.error("Couldn't create Command: "+c.getName());
                        Strings.error(new StackTraceElement("CommandManager", "loadModules", "ModuleManager.java", 37).toString());
                        System.err.println("Error found! Couldn't create Module: "+c.getName());
                    }
                }
            }
        }
    }

    public List<Command> commands() {
        return commands;
    }
    public Command command(String name) {
        for(Command command : commands) {
            CommandInfo info = commandInfo(command);
            if(isCommand(name, info)) return command;
        }
        return null;
    }
    public boolean isCommand(String name, CommandInfo info) {
        if(info.name().toLowerCase().equals(name)) {
            return true;
        }
        return false;
    }
    public CommandInfo commandInfo(Command cmd) {
        return cmd.getClass().getAnnotation(CommandInfo.class);
    }

}
