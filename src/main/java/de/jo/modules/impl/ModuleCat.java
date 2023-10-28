package de.jo.modules.impl;

import de.jo.modules.Module;
import de.jo.modules.ModuleInfo;
import de.jo.util.ConsoleColors;
import de.jo.util.Files;
import de.jo.util.Strings;

import java.io.File;
import java.util.List;

/**
 * @author Johannes Hans 27.10.2023
 * @Project ConsoleTools
 */
@ModuleInfo(name = "cat", description = "Prints the contents of a file to the console", syntax = "<File>")
public class ModuleCat implements Module {
    @Override
    public void run(String... args) {
        if(args.length > 0) {
            String filename = args[0];
            if(!new File(filename).exists()) {
                Strings.error("File: \""+filename+"\" not found");
                Strings.error(new StackTraceElement("ModuleCat", "run", "ModuleCat.java", 24).toString());
                return;
            }
            List<String> lines = Files.lines(filename);
            System.out.println(ConsoleColors.YELLOW_BRIGHT+"---------------------------------");
            for(int i = 0; i < lines.size(); i++) {
                int spaces = (""+lines.size()).length()-(""+i).length()+1;
                StringBuilder out = new StringBuilder(ConsoleColors.YELLOW_BRIGHT + "|" + i);
                out.append(Strings.repeat(spaces, " "));
                out.append(": ");
                out.append(ConsoleColors.YELLOW+lines.get(i));
                System.out.println(out);
            }
            System.out.println(ConsoleColors.YELLOW_BRIGHT+"---------------------------------");
        }
    }
}
