package de.jo.modules.impl.other;

import de.jo.math.Math;
import de.jo.modules.Module;
import de.jo.modules.ModuleInfo;
import de.jo.util.ConsoleColors;
import de.jo.util.Strings;
import org.mariuszgromada.math.mxparser.*;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.util.Scanner;

/**
 * @author Johannes Hans 05.12.2023
 * @Project ConsoleTools
 */
@ModuleInfo(name = "math", description = "", syntax = "<Expression>")
public class ModuleMath implements Module {

    private Math math;

    @Override
    public void run(String... args) throws Exception {
        this.math = new Math();
        boolean isMath = true, isVerbose = false, isCopy = false;
        System.out.println(ConsoleColors.YELLOW+"> "+ConsoleColors.YELLOW_BRIGHT+"You're now in Math-Mode!");
        System.out.println(ConsoleColors.YELLOW_BRIGHT+"---------------------------------");
        System.out.println(ConsoleColors.YELLOW+"> "+ConsoleColors.YELLOW_BRIGHT+"Use the command \":f Function\" to write functions");
        System.out.println(ConsoleColors.YELLOW+"> "+ConsoleColors.YELLOW_BRIGHT+"Use the command \":d Function\" to write get a quick derivative function named functionname+\"d\"");
        System.out.println(ConsoleColors.YELLOW+"> "+ConsoleColors.YELLOW_BRIGHT+"Use the command \":s\" to save the result to clipboard automatically");
        System.out.println(ConsoleColors.YELLOW+"> "+ConsoleColors.YELLOW_BRIGHT+"Use the command \":c Const\" to write constants");
        System.out.println(ConsoleColors.YELLOW+"> "+ConsoleColors.YELLOW_BRIGHT+"Use the command \":e Expression\" to evaluate expressions");
        System.out.println(ConsoleColors.YELLOW+"> "+ConsoleColors.YELLOW_BRIGHT+"Use the command \":v\" to enable/disable calculating documentation");
        System.out.println(ConsoleColors.YELLOW+"> "+ConsoleColors.YELLOW_BRIGHT+"Use the command \":q\" to quit");
        System.out.println(ConsoleColors.YELLOW+"> "+ConsoleColors.YELLOW_BRIGHT+"For more documentation visit https://mathparser.org/mxparser-tutorial/");
        System.out.println(ConsoleColors.YELLOW_BRIGHT+"---------------------------------");
        
        while(isMath) {
            Scanner scanner = new Scanner(System.in);
            String line = "";
            while((line = scanner.nextLine()) != null) {
                if(line.isEmpty()) {
                    System.out.println();
                }
                if(line.toLowerCase().startsWith(":f")) {
                    Function function = new Function(line.substring(2));
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
                else if(line.toLowerCase().startsWith(":d")) {
                    String fun = line.substring(2).trim();
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
                else if(line.toLowerCase().startsWith(":s")) {
                    isCopy = !isCopy;
                    System.out.println(ConsoleColors.GREEN+"> "+(isCopy ? "enabled" : "disabled")+" auto result copy");
                }
                else if(line.toLowerCase().startsWith(":c")) {
                    Constant cons = new Constant(line.substring(2));
                    if(!math.constants().containsKey(cons.getConstantName())) {
                        math.constants().put(cons.getConstantName(), cons);
                    }else {
                        math.constants().replace(cons.getConstantName(), cons);
                    }
                    System.out.println(ConsoleColors.GREEN+"> "+cons.getConstantValue());
                } else if(line.toLowerCase().startsWith(":e")){
                    Expression expression = new Expression(line.substring(2));
                    math.functions().values().forEach(expression::addDefinitions);
                    math.constants().values().forEach(expression::addDefinitions);
                    expression.enableImpliedMultiplicationMode();
                    if(isVerbose) expression.setVerboseMode();

                    Object obj = expression.calculate();
                    copy(obj.toString());
                    System.out.println(ConsoleColors.GREEN+"> "+obj.toString());
                }else if(line.toLowerCase().startsWith(":v")){
                    isVerbose = !isVerbose;
                    System.out.println(ConsoleColors.GREEN+"> "+(isVerbose ? "enabled" : "disabled")+" calculation documentation");
                }else{
                    if(line.equals(":q")) {
                        math.functions().clear();
                        math.constants().clear();;
                        isMath = false;
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

    public void copy(String result) {
        StringSelection selection = new StringSelection(result);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, selection);
    }

}
