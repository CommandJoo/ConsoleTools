package de.jo.modules.impl;

import de.jo.modules.Module;
import de.jo.modules.ModuleInfo;

/**
 * @author Johannes Hans 27.10.2023
 * @Project ConsoleTools
 */
@ModuleInfo(name = "exit", description = "Exits the CLI", aliases = {"--", "ext"})
public class ModuleExit implements Module {


    @Override
    public void run(String... args) {
        System.exit(0);
    }
}
