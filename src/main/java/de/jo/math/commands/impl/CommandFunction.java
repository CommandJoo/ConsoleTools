package de.jo.math.commands.impl;

import de.jo.math.Math;
import de.jo.math.commands.Command;
import de.jo.math.commands.CommandInfo;
import de.jo.util.ConsoleColors;
import org.mariuszgromada.math.mxparser.Function;

@CommandInfo(name = "f", description = "Creates a function",syntax = "<Function>")
public class CommandFunction implements Command {

    @Override
    public void run(Math math, String... args) throws Exception {
        Function function = new Function(args[0]);
        math.functions().values().forEach(f -> {
            if(!f.getFunctionName().equals(function.getFunctionName())) {
                function.addDefinitions(f);
            }
        });
        math.constants().values().forEach(f -> {
            if(!f.getConstantName().equals(function.getFunctionName())) {
                function.addDefinitions(f);
            }
        });
        if(!math.functions().containsKey(function.getFunctionName())) {
            math.functions().put(function.getFunctionName(), function);
        }else {
            math.functions().replace(function.getFunctionName(), function);
        }
        System.out.println(ConsoleColors.GREEN+"> "+function.getFunctionName()+": "+function.getFunctionExpressionString());
    }
}
