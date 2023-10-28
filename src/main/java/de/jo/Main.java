package de.jo;

import de.jo.modules.ModuleInfo;
import de.jo.options.Option;
import de.jo.options.Options;
import de.jo.util.ConsoleColors;
import de.jo.util.PackageScanner;
import de.jo.util.Strings;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import org.apache.log4j.BasicConfigurator;

import java.io.PrintStream;
import java.util.Arrays;

/**
 * @author Johannes Hans 27.10.2023
 * @Project ConsoleTools
 */
public class Main {

    public static void main(String[] args) {
        BasicConfigurator.configure();
        new ConsoleTools(args);
    }

}
