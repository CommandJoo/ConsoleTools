package de.jo.modules.impl.url;

import Jwiki.Jwiki;
import de.jo.modules.Module;
import de.jo.modules.ModuleInfo;
import de.jo.util.ConsoleColors;
import de.jo.util.Strings;

import java.awt.color.ColorSpace;

/**
 * @author Johannes Hans 31.10.2023
 * @Project ConsoleTools
 */
@ModuleInfo(name = "summary", description = "Prints the wikipedia summary of a given topic", aliases = "summ", syntax = "<Topic>")
public class ModuleSummarize implements Module {
    @Override
    public void run(String... args) throws Exception {
        if(args.length == 1) {
            try {
                Jwiki jwiki = new Jwiki(args[0]);
                System.out.println(ConsoleColors.YELLOW+"> "+ConsoleColors.YELLOW_BRIGHT+"Searching for: "+args[0]+"\n");
                System.out.println(ConsoleColors.YELLOW_BRIGHT+jwiki.getExtractText());
            } catch(NullPointerException ex) {
                Strings.error("Topic not found!");
                Strings.moduleError(this, 26);
            }
        }else{
            Strings.error("Invalid usage");
        }
    }
}
