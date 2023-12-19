package de.jo.modules.impl.other;

import de.jo.math.Math;
import de.jo.math.commands.Command;
import de.jo.math.commands.CommandManager;
import de.jo.modules.Module;
import de.jo.modules.ModuleInfo;
import de.jo.util.ConsoleColors;
import de.jo.util.Strings;
import org.mariuszgromada.math.mxparser.*;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Johannes Hans 05.12.2023
 * @Project ConsoleTools
 */
@ModuleInfo(name = "math", description = "", syntax = "<Expression>")
public class ModuleMath implements Module {

    private Math math;
    private CommandManager manager;

    public ModuleMath() {
        this.manager = new CommandManager();
    }

    @Override
    public void run(String... args) throws Exception {
        this.math = new Math();
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
        
        while(math.isMath) {
            Scanner scanner = new Scanner(System.in);
            String line = "";
            while((line = scanner.nextLine()) != null) {
                if(line.length()==0) {
                    continue;
                }
                if(line.startsWith(":")) {
                    listen(line.substring(1));
                }
                else{
                        Strings.error("Invalid operation");
                }
                if(!math.isMath) break;
            }
        }
        System.out.println(ConsoleColors.YELLOW_BRIGHT+"---------------------------------");
        System.out.println(ConsoleColors.YELLOW+"> "+ConsoleColors.YELLOW_BRIGHT+"Done with math!");
        System.out.println();
    }

    public void listen(String input) {
        String[] split = splitArgs(input);
        String cmd = split[0];
        Command command = this.manager.command(cmd);
        String[] args = Arrays.copyOfRange(split, 1, split.length);
        if(command != null) {
            try {
                command.run(math, args);
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }else{
            System.out.println(ConsoleColors.RED_BRIGHT+"Command \""+cmd+"\" not found!");
            ConsoleColors.reset();
        }
    }

    public String[] splitArgs(String input) {
        return input.split(" ", 2);
    }

}
