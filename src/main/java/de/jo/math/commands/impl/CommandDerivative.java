package de.jo.math.commands.impl;

import de.jo.math.Math;
import de.jo.math.commands.Command;
import de.jo.math.commands.CommandInfo;
import de.jo.util.ConsoleColors;
import de.jo.util.Strings;
import org.mariuszgromada.math.mxparser.Function;

@CommandInfo(name ="d", description = "Creates the derivative of a function, named functionname+\"d\"", syntax = "<Functionname>")
public class CommandDerivative implements Command {
    @Override
    public void run(Math math, String... args) throws Exception {
        String fun = args[0].trim();
        if(!math.functions().containsKey(fun)) {
            Strings.error("Invalid function name: "+fun);
        }else {
            Function function = math.functions().get(fun);

            Function derivative = new Function(fun+"d", "der("+fun+"(y), y, x)", "x");
            math.functions().values().forEach(f -> {
                if(!f.getFunctionName().equals(derivative.getFunctionName())) {
                    derivative.addDefinitions(f);
                }
            });
            if(!math.functions().containsKey(derivative.getFunctionName())) {
                math.functions().put(derivative.getFunctionName(), derivative);
            }else {
                math.functions().replace(derivative.getFunctionName(), derivative);
            }
            System.out.println(ConsoleColors.GREEN+"> "+derivative.getFunctionName()+": "+derivative.getFunctionExpressionString());
        }
    }
}
