package de.jo.math.commands.impl;

import de.jo.math.Math;
import de.jo.math.commands.Command;
import de.jo.math.commands.CommandInfo;
import de.jo.util.console.ConsoleColors;

@CommandInfo(name="v", description = "Prints the calculation path for :e", syntax = "")
public class CommandVerbose implements Command {

    @Override
    public void run(Math math, String... args) throws Exception {
        math.isVerbose = !math.isVerbose;
        System.out.println(ConsoleColors.GREEN+"> "+(math.isVerbose ? "enabled" : "disabled")+" verbose mode for calculation path");
//        System.out.print("\033[A\033[A\033[A");
    }
}
