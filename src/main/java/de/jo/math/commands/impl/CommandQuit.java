package de.jo.math.commands.impl;

import de.jo.math.Math;
import de.jo.math.commands.Command;
import de.jo.math.commands.CommandInfo;

@CommandInfo(name = "q",description = "closes the math subprogram", syntax = "")
public class CommandQuit implements Command {
    @Override
    public void run(Math math, String... args) throws Exception {
        math.functions().clear();
        math.constants().clear();;
        math.isMath = false;
    }
}
