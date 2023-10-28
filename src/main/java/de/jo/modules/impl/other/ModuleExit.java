package de.jo.modules.impl.other;

import de.jo.modules.Module;
import de.jo.modules.ModuleInfo;

/**
 * @author Johannes Hans 27.10.2023
 * @Project ConsoleTools
 */
@ModuleInfo(name = "exit", description = "Exits the CLI", aliases = {"--", "ext"}, syntax = "<Code (Optional)>")
public class ModuleExit implements Module {


    @Override
    public void run(String... args) {
        if(args.length == 0) {
            System.exit(0);
        }else {
            try {
                System.exit(Integer.parseInt(args[0]));
            } catch(Exception ex) {
                System.exit(0);
            }
        }
    }
}
