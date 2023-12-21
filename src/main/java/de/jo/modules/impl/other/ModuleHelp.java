package de.jo.modules.impl.other;

import de.jo.ConsoleTools;
import de.jo.modules.Module;
import de.jo.modules.ModuleInfo;
import de.jo.util.console.ConsoleColors;

/**
 * @author Johannes Hans 27.10.2023
 * @Project ConsoleTools
 */
@ModuleInfo(name = "help", description = "Prints the help screen", aliases = {"?"})
public class ModuleHelp implements Module {
    @Override
    public void run(String[] args) {
        System.out.println(ConsoleColors.YELLOW_BOLD_BRIGHT+"---------------------------------");
        for(Module module : ConsoleTools.instance().manager().modules()) {
            printModuleInfo(module);
        }
        System.out.println(ConsoleColors.YELLOW_BRIGHT+"---------------------------------");
        System.out.println(ConsoleColors.RESET);
    }

    public static void printModuleInfo(Module module) {
        ModuleInfo info = ConsoleTools.instance().manager().moduleInfo(module);
        System.out.println(ConsoleColors.YELLOW_BOLD_BRIGHT + info.name() + ConsoleColors.YELLOW + " - " + info.description());
        //print aliases
        if (info.aliases().length > 0) {
            StringBuilder aliases = new StringBuilder(ConsoleColors.YELLOW_BOLD_BRIGHT + "     aliases: " + ConsoleColors.YELLOW);
            for (int i = 0; i < info.aliases().length; i++) {
                if (i > 0) {
                    aliases.append(", ").append("\"").append(info.aliases()[i]).append("\"");
                } else {
                    aliases.append("\"").append(info.aliases()[i]).append("\"");
                }
            }
            System.out.println(ConsoleColors.YELLOW_BOLD_BRIGHT + aliases);
        }
        //print syntax
        if (!info.syntax().isEmpty()) {
            StringBuilder syntax = new StringBuilder(ConsoleColors.YELLOW_BOLD_BRIGHT + "     usage: " + info.name() + " " + info.syntax() + ConsoleColors.YELLOW);
            System.out.println(ConsoleColors.YELLOW_BOLD_BRIGHT + syntax);
        }
    }

    public static String moduleInfo(Module module) {
        ModuleInfo info = ConsoleTools.instance().manager().moduleInfo(module);

        String out = "";

        out = ConsoleColors.YELLOW_BOLD_BRIGHT + info.name() + ConsoleColors.YELLOW + " - " + info.description() + "\n";
        //print aliases
        if (info.aliases().length > 0) {
            StringBuilder aliases = new StringBuilder(ConsoleColors.YELLOW_BOLD_BRIGHT + "     aliases: " + ConsoleColors.YELLOW);
            for (int i = 0; i < info.aliases().length; i++) {
                if (i > 0) {
                    aliases.append(", ").append("\"").append(info.aliases()[i]).append("\"");
                } else {
                    aliases.append("\"").append(info.aliases()[i]).append("\"");
                }
            }
            out += ConsoleColors.YELLOW_BOLD_BRIGHT + aliases + "\n";
        }
        //print syntax
        if (!info.syntax().isEmpty()) {
            StringBuilder syntax = new StringBuilder(ConsoleColors.YELLOW_BOLD_BRIGHT + "     usage: " + info.name() + " " + info.syntax() + ConsoleColors.YELLOW);
           out += ConsoleColors.YELLOW_BOLD_BRIGHT + syntax + "\n";
        }
        return out;
    }
}
