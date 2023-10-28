package de.jo.modules.impl.files;

import de.jo.modules.Module;
import de.jo.modules.ModuleInfo;
import de.jo.util.ConsoleColors;
import de.jo.util.Files;
import de.jo.util.Strings;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author Johannes Hans 28.10.2023
 * @Project ConsoleTools
 */
@ModuleInfo(name = "scan", description = "Scans a file for a certain word", aliases = {"scn", "find"}, syntax = "($/)<File> <Term>")
public class ModuleScan implements Module {
    @Override
    public void run(String... args) throws IOException, InterruptedException {
        if(args.length > 1){
                String filename = Files.parseFile(args[0]);
                String searchterm = args[1];
                if(!new File(filename).exists()) {
                    Strings.error("File: \""+filename+"\" not found");
                    Strings.error(new StackTraceElement("ModuleCat", "run", "ModuleCat.java", 24).toString());
                    return;
                }
                List<String> lines = Files.lines(filename);
                long startTime = System.currentTimeMillis();
                System.out.println(ConsoleColors.YELLOW_BRIGHT+"---------------------------------");
                for(int i = 0; i < lines.size(); i++) {
                    String s = lines.get(i);
                    if(s.contains(searchterm.trim())) {
                        long time = (System.currentTimeMillis()-startTime);
                        System.out.println(ConsoleColors.YELLOW+"Found keyword in line: "+ConsoleColors.YELLOW_BRIGHT+i+ConsoleColors.YELLOW+" in "+ConsoleColors.YELLOW_BRIGHT+(time <= 100 ? time+"ms" : (time/1000F)+"s"));
                    }
                }
                System.out.println(ConsoleColors.YELLOW_BRIGHT+"---------------------------------");
        }else {
            Strings.error("Invalid usage");
        }
    }
}
