package de.jo.modules.impl;

import de.jo.ConsoleTools;
import de.jo.modules.Module;
import de.jo.modules.ModuleInfo;
import de.jo.util.ConsoleColors;

/**
 * @author Johannes Hans 27.10.2023
 * @Project ConsoleTools
 */
@ModuleInfo(name = "help", description = "Prints the help screen", aliases = {"?"})
public class ModuleHelp implements Module {
    @Override
    public void run(String[] args) {
        System.out.println(ConsoleColors.YELLOW_BRIGHT+"---------------------------------");
        for(Module module : ConsoleTools.instance().manager().modules()) {
            ModuleInfo info = ConsoleTools.instance().manager().moduleInfo(module);
            if(info.aliases().length == 0) {
                System.out.println(ConsoleColors.YELLOW_BOLD+info.name()+ConsoleColors.YELLOW+" - "+info.description());
            }else {
                System.out.println(ConsoleColors.YELLOW_BOLD+info.name()+ConsoleColors.YELLOW+" - "+info.description());
                StringBuilder aliases = new StringBuilder("  aliases: ");
                for(int i = 0; i < info.aliases().length; i++) {
                    if(i > 0) {
                        aliases.append(", ").append("\"").append(info.aliases()[i]).append("\"");
                    }else {
                        aliases.append("\"").append(info.aliases()[i]).append("\"");
                    }
                }
                System.out.println(ConsoleColors.YELLOW+aliases);
            }
        }
        System.out.println(ConsoleColors.YELLOW_BRIGHT+"---------------------------------");
        ConsoleColors.reset();
    }
}
