package de.jo.modules.impl.files;

import de.jo.modules.Module;
import de.jo.modules.ModuleInfo;
import de.jo.util.Files;
import de.jo.util.Strings;

import java.io.File;

/**
 * @author Johannes Hans 29.10.2023
 * @Project ConsoleTools
 */
@ModuleInfo(name = "rename", description = "Renames a file", aliases = "rn", syntax = "<File> <Name>")
public class ModuleRename implements Module {
    @Override
    public void run(String... args) throws Exception {
        if(args.length == 2) {
            File file = new File(Files.parseFile(args[0]));
            if(!file.exists()) {
                Strings.error("Invalid file: "+file.getCanonicalFile());
                Strings.moduleError(this, 22);
                return;
            }
            String name = args[1];
            if(!Files.isValidFileName(name)) {
                Strings.error("Invalid filename: "+name);
                Strings.moduleError(this, 28);
                return;
            }
            file.renameTo(new File(file.getParent(), name));
        }else {
            Strings.error("Invalid usage");
        }
    }
}
