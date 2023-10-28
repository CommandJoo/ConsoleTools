package de.jo.modules.impl.files;

import de.jo.ConsoleTools;
import de.jo.modules.Module;
import de.jo.modules.ModuleInfo;
import de.jo.util.ConsoleColors;
import de.jo.util.Strings;

import java.io.File;

/**
 * @author Johannes Hans 28.10.2023
 * @Project ConsoleTools
 */
@ModuleInfo(name = "changedirectory", description = "Changes the current directory ($/) to another", aliases = {"changedir", "cd"}, syntax = "<Path>")
public class ModuleChangeDirectory implements Module {
    @Override
    public void run(String... args) throws Exception {
        if(args.length > 0) {
            String fileName = args[0];
            //                                  D: --> absolut path
            File newDir = fileName.charAt(1) == ':' ? new File(fileName) : new File(ConsoleTools.instance().currentDirectory, fileName);
            if(!newDir.exists() || !newDir.isDirectory()) {
                Strings.error("Invalid destination: "+newDir.getCanonicalFile());
                return;
            }
            ConsoleTools.instance().currentDirectory = newDir.getCanonicalFile();
            System.out.println(ConsoleColors.YELLOW_BRIGHT+"> "+ConsoleColors.YELLOW+newDir.getCanonicalFile());
        }
    }
}
