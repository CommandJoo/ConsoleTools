package de.jo;

import de.jo.modules.Module;
import de.jo.modules.ModuleInfo;
import de.jo.modules.ModuleManager;
import de.jo.options.Option;
import de.jo.options.Options;
import de.jo.util.ConsoleColors;
import de.jo.util.Files;
import de.jo.util.PackageScanner;
import de.jo.util.Strings;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;

import java.io.IOException;
import java.io.PrintStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        Strings.clearScreen();
        this.logo();
        this.consoleStyle();

        OptionSet ops = options.build(args);
        ops.nonOptionArguments().forEach(arg -> {
            if(arg.toString().equals("help") || arg.toString().equals("h") || arg.toString().equals("?")) {
                try {
                    this.manager.module("help").run("");
                } catch (IOException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
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
        System.out.println(ConsoleColors.BLACK_BACKGROUND+"                                                                                                                       "+ConsoleColors.RESET);
        for(String s : Files.sysLines("logo.txt")) {
            System.out.println(ConsoleColors.BLACK_BACKGROUND+ConsoleColors.BLUE_BOLD_BRIGHT+s+ConsoleColors.RESET);
        }
        System.out.println(ConsoleColors.BLACK_BACKGROUND+"                                                                                                                       "+ConsoleColors.RESET);
        ConsoleColors.reset();
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }


    public void consoleStyle() {
        PrintStream ps = new PrintStream(System.out) {
            @Override
            public void println(Object x) {
                super.println(ConsoleColors.YELLOW+x);
            }
        };
        System.setOut(ps);
    }


    public void listen(String input) {
        String[] split = splitArgs(input);

        String mod = split[0];
        Module module = this.manager.module(mod);
        String[] args = Arrays.copyOfRange(split, 1, split.length);

        if(module != null) {
            try {
                module.run(args);
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }else{
            System.out.println(ConsoleColors.RED_BRIGHT+"Module \""+mod+"\" not found!");
            ConsoleColors.reset();
        }
    }

    private String[] splitArgs(String input) {
        List<String> list = new ArrayList<String>();
        Matcher m = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(input);
        while (m.find())
            list.add(m.group(1).replace("\"", ""));
        String[] split = new String[list.size()];
        for(int i = 0; i < list.size(); i++) {
            split[i] = list.get(i);
        }
        return split;
    }


    public static ConsoleTools instance() {
        return instance;
    }
    public ModuleManager manager() {
        return manager;
    }
}
