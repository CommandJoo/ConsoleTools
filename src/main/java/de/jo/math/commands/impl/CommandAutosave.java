package de.jo.math.commands.impl;

import de.jo.math.Math;
import de.jo.math.commands.Command;
import de.jo.math.commands.CommandInfo;
import de.jo.util.console.ConsoleColors;

@CommandInfo(name="s", description = "Automatically saves last output to clipboard", syntax = "")
public class CommandAutosave implements Command {

    @Override
    public void run(Math math, String... args) throws Exception {
        math.isCopy = !math.isCopy;
        System.out.println(ConsoleColors.GREEN+"> "+(math.isCopy ? "enabled" : "disabled")+" auto result copy");
    }
}
