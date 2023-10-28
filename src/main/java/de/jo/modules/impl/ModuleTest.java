package de.jo.modules.impl;

import de.jo.modules.Module;
import de.jo.modules.ModuleInfo;

import java.io.IOException;
import java.util.Arrays;

/**
 * @author Johannes Hans 28.10.2023
 * @Project ConsoleTools
 */
@ModuleInfo(name = "test", description = "A Test Module to test the parsing of args")
public class ModuleTest implements Module {


    @Override
    public void run(String... args) throws IOException, InterruptedException {
    }
}
