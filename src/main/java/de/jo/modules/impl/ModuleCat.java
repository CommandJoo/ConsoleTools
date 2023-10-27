package de.jo.modules.impl;

import de.jo.modules.Module;
import de.jo.modules.ModuleInfo;
import de.jo.util.ConsoleColors;
import de.jo.util.Files;

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
            if(!new File(filename).exists()) return;
            List<String> lines = Files.lines(filename);
            System.out.println(ConsoleColors.YELLOW_BRIGHT+"---------------------------------");
            for(int i = 0; i < lines.size(); i++) {
                int spaces = (""+lines.size()).length()-(""+i).length()+1;
                StringBuilder out = new StringBuilder(ConsoleColors.YELLOW_BRIGHT + i);
                for(int j = 0; j < spaces; j++) {out.append(" ");}
                out.append(": ");
                out.append(lines.get(i));
                System.out.println(out);
            }
            System.out.println(ConsoleColors.YELLOW_BRIGHT+"---------------------------------");
        }
    }
}
