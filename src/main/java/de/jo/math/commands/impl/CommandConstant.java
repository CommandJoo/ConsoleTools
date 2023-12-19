package de.jo.math.commands.impl;

import de.jo.math.Math;
import de.jo.math.commands.Command;
import de.jo.math.commands.CommandInfo;
import de.jo.util.ConsoleColors;
import org.mariuszgromada.math.mxparser.Constant;

@CommandInfo(name = "c", description = "Saves a constant", syntax = "<Name>=<Value>")
public class CommandConstant implements Command {
    @Override
    public void run(Math math, String... args) throws Exception {
        Constant cons = new Constant(args[0]);
        if(!math.constants().containsKey(cons.getConstantName())) {
            math.constants().put(cons.getConstantName(), cons);
        }else {
            math.constants().replace(cons.getConstantName(), cons);
        }
        System.out.println(ConsoleColors.GREEN+"> "+cons.getConstantValue());
    }
}
