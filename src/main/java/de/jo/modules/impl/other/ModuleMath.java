package de.jo.modules.impl.other;

import de.jo.modules.Module;
import de.jo.modules.ModuleInfo;
import de.jo.util.ConsoleColors;
import de.jo.util.Strings;
import org.mariuszgromada.math.mxparser.Constant;
import org.mariuszgromada.math.mxparser.Expression;
import org.mariuszgromada.math.mxparser.Function;
import org.mariuszgromada.math.mxparser.mXparser;

import java.util.HashMap;
import java.util.Scanner;

/**
 * @author Johannes Hans 05.12.2023
 * @Project ConsoleTools
 */
@ModuleInfo(name = "math", description = "", syntax = "<Expression>")
public class ModuleMath implements Module {

    private final HashMap<String, Function> functions = new HashMap<>();
    private final HashMap<String, Constant> constants = new HashMap<>();

    @Override
    public void run(String... args) throws Exception {
        boolean math = true;
        boolean verbose = false;
        System.out.println(ConsoleColors.YELLOW+"> "+ConsoleColors.YELLOW_BRIGHT+"You're now in Math-Mode!");
        System.out.println(ConsoleColors.YELLOW_BRIGHT+"---------------------------------");
        System.out.println(ConsoleColors.YELLOW+"> "+ConsoleColors.YELLOW_BRIGHT+"Use the command \":f Function\" to write functions");
        System.out.println(ConsoleColors.YELLOW+"> "+ConsoleColors.YELLOW_BRIGHT+"Use the command \":d Function\" to write get a quick derivative function named functionname+\"d\"");
        System.out.println(ConsoleColors.YELLOW+"> "+ConsoleColors.YELLOW_BRIGHT+"Use the command \":c Const\" to write constants");
        System.out.println(ConsoleColors.YELLOW+"> "+ConsoleColors.YELLOW_BRIGHT+"Use the command \":e Expression\" to evaluate expressions");
        System.out.println(ConsoleColors.YELLOW+"> "+ConsoleColors.YELLOW_BRIGHT+"Use the command \":v\" to enable/disable calculating documentation");
        System.out.println(ConsoleColors.YELLOW+"> "+ConsoleColors.YELLOW_BRIGHT+"Use the command \"exit\" to quit");
        System.out.println(ConsoleColors.YELLOW+"> "+ConsoleColors.YELLOW_BRIGHT+"For more documentation visit https://mathparser.org/mxparser-tutorial/");
        System.out.println(ConsoleColors.YELLOW_BRIGHT+"---------------------------------");

        while(math) {
            Scanner scanner = new Scanner(System.in);
            String line = "";
            while((line = scanner.nextLine()) != null) {
                if(line.toLowerCase().startsWith(":f")) {
                    Function function = new Function(line.substring(2));
                    functions.values().forEach(f -> {
                        if(!f.getFunctionName().equals(function.getFunctionName())) {
                            function.addDefinitions(f);
                        }
                    });
                    constants.values().forEach(f -> {
                        if(!f.getConstantName().equals(function.getFunctionName())) {
                            function.addDefinitions(f);
                        }
                    });
                    if(!functions.containsKey(function.getFunctionName())) {
                        functions.put(function.getFunctionName(), function);
                    }else {
                        functions.replace(function.getFunctionName(), function);
                    }
                    System.out.println(ConsoleColors.GREEN+"> "+function.getFunctionName()+": "+function.getFunctionExpressionString());
                }
                else if(line.toLowerCase().startsWith(":d")) {
                    String fun = line.substring(2).trim();
                    if(!functions.containsKey(fun)) {
                        Strings.error("Invalid function name: "+fun);
                    }else {
                        Function function = functions.get(fun);

                        Function derivative = new Function(fun+"d", "der("+fun+"(y), y, x)", "x");
                        functions.values().forEach(f -> {
                            if(!f.getFunctionName().equals(derivative.getFunctionName())) {
                                derivative.addDefinitions(f);
                            }
                        });
                        if(!functions.containsKey(derivative.getFunctionName())) {
                            functions.put(derivative.getFunctionName(), derivative);
                        }else {
                            functions.replace(derivative.getFunctionName(), derivative);
                        }
                        System.out.println(ConsoleColors.GREEN+"> "+derivative.getFunctionName()+": "+derivative.getFunctionExpressionString());
                    }
                }
                else if(line.toLowerCase().startsWith(":c")) {
                    Constant cons = new Constant(line.substring(2));
                    if(!constants.containsKey(cons.getConstantName())) {
                        constants.put(cons.getConstantName(), cons);
                    }else {
                        constants.replace(cons.getConstantName(), cons);
                    }
                    System.out.println(ConsoleColors.GREEN+"> "+cons.getConstantValue());
                } else if(line.toLowerCase().startsWith(":e")){
                    Expression expression = new Expression(line.substring(2));
                    functions.values().forEach(expression::addDefinitions);
                    constants.values().forEach(expression::addDefinitions);
                    expression.enableImpliedMultiplicationMode();
                    if(verbose) expression.setVerboseMode();
                    System.out.println(ConsoleColors.GREEN+"> "+expression.calculate());
                }else if(line.toLowerCase().startsWith(":v")){
                    verbose = !verbose;
                    System.out.println(ConsoleColors.GREEN+"> "+(verbose ? "enabled" : "disabled")+" calculation documentation");
                }else{
                    if(line.equals("exit")) {
                        functions.clear();
                        constants.clear();;
                        math = false;
                        break;
                    }else {
                        Strings.error("Invalid operation");
                    }
                }
            }


        }
        System.out.println(ConsoleColors.YELLOW_BRIGHT+"---------------------------------");
        System.out.println(ConsoleColors.YELLOW+"> "+ConsoleColors.YELLOW_BRIGHT+"Done with math!");
        System.out.println();
    }
}