package de.jo.modules.impl.files;

import de.jo.modules.Module;
import de.jo.modules.ModuleInfo;
import de.jo.modules.impl.other.ModuleHelp;
import de.jo.util.ConsoleColors;
import de.jo.util.Files;
import de.jo.util.Strings;

import java.io.File;

/**
 * @author Johannes Hans 29.10.2023
 * @Project ConsoleTools
 */
@ModuleInfo(name = "json", description = "Prints beautified json to the console", syntax = "<--f|--s> <File|String>")
public class ModuleJson implements Module {
    @Override
    public void run(String... args) throws Exception {
        if(args.length == 2) {
            if(args[0].equals("--f")) {
                File file = new File(Files.parseFile(args[1]));
                if(!file.exists()) {
                    Strings.error("File: \""+file.getCanonicalPath()+"\" not found");
                    Strings.error(Strings.errorMessage(this, "run", 44));
                    return;
                }
                String json = Strings.lines(Files.lines(file));
                System.out.println(ConsoleColors.YELLOW_BRIGHT+"---------------------------------");
                System.out.println(Strings.json(json));
                System.out.println(ConsoleColors.YELLOW_BRIGHT+"---------------------------------");
            }else if(args[0].equals("--t")) {
                String json = args[1];
                System.out.println(ConsoleColors.YELLOW_BRIGHT+"---------------------------------");
                System.out.println(Strings.json(json));
                System.out.println(ConsoleColors.YELLOW_BRIGHT+"---------------------------------");
            }else {
                Strings.error("Invalid argument");
                Strings.error(ModuleHelp.moduleInfo(this));
            }
        }else {
            Strings.error("Invalid usage");
        }
    }
}
