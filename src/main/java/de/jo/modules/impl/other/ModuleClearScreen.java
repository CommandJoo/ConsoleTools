package de.jo.modules.impl.other;

import de.jo.ConsoleTools;
import de.jo.modules.Module;
import de.jo.modules.ModuleInfo;
import de.jo.util.Strings;

import java.io.IOException;

/**
 * @author Johannes Hans 27.10.2023
 * @Project ConsoleTools
 */
@ModuleInfo(name = "clearscreen", description = "Clears the screen of all text", aliases = "cls")
public class ModuleClearScreen implements Module {
    @Override
    public void run(String... args) throws IOException, InterruptedException {
        for (int i = 0; i < 2; i++) {
            Strings.clearScreen();
        }
        ConsoleTools.instance().logo();
    }
}
