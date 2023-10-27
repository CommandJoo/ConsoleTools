package de.jo;

import de.jo.modules.Module;
import de.jo.modules.ModuleInfo;
import de.jo.modules.ModuleManager;
import de.jo.options.Option;
import de.jo.options.Options;
import de.jo.util.ConsoleColors;
import de.jo.util.Files;
import de.jo.util.PackageScanner;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @author Johannes Hans 27.10.2023
 * @Project ConsoleTools
 */
public class ConsoleTools {

    private final Options options;
    private final ModuleManager manager;

    private static ConsoleTools instance;

    public ConsoleTools(String[] args) {
        ConsoleTools.instance = this;
        this.options = new Options();
        this.manager = new ModuleManager();

        this.logo();

        OptionSet ops = options.build(args);
        ops.nonOptionArguments().forEach(arg -> {
            if(arg.toString().equals("help") || arg.toString().equals("h") || arg.toString().equals("?")) {
                this.manager.module("help").run("");
            }
        });

        Scanner input = new Scanner(System.in);
        String s = "";
        try {
            while((s = input.nextLine()) != null) {
                listen(s);
            }
        } catch(Exception ex) {

        }
    }


    public void logo() {
        for(String s : Files.sysLines("logo.txt")) {
            System.out.println(ConsoleColors.BLACK_BACKGROUND+ConsoleColors.BLUE_BOLD_BRIGHT+s);
        }
    }



    public void listen(String input) {
        String[] split = input.split(" ");
        String mod = split[0];
        Module module = this.manager.module(mod);
        String[] args = Arrays.copyOfRange(split, 1, split.length);

        if(module != null) {
            module.run(args);
        }else{
            System.out.println(ConsoleColors.RED_BRIGHT+"Module \""+mod+"\" not found!");
            ConsoleColors.reset();
        }
    }









    public static ConsoleTools instance() {
        return instance;
    }
    public ModuleManager manager() {
        return manager;
    }
}
