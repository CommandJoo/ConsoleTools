package de.jo.modules.impl;

import de.jo.modules.Module;
import de.jo.modules.ModuleInfo;
import de.jo.util.ConsoleColors;
import de.jo.util.Files;
import de.jo.util.Strings;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * @author Johannes Hans 27.10.2023
 * @Project ConsoleTools
 */
@ModuleInfo(name = "ucat", description = "Prints the contents of a url to the console", syntax = "<URL>")
public class ModuleUCat implements Module {
    @Override
    public void run(String... args) throws IOException {
        if(args.length > 0) {
            String urls = args[0];
            URL url = new URL(urls);
            InputStream is = url.openStream();
            if(is == null) {
                Strings.error("URL: \""+urls+"\" not found");
                Strings.error(new StackTraceElement("ModuleUCat", "run", "ModuleUCat.java", 30).toString());
                return;
            }
            List<String> lines = Files.lines(is);
            System.out.println(ConsoleColors.YELLOW_BRIGHT+"---------------------------------");
            for(int i = 0; i < lines.size(); i++) {
                int spaces = (""+lines.size()).length()-(""+i).length()+1;
                StringBuilder out = new StringBuilder(ConsoleColors.YELLOW_BRIGHT + "|" + i);
                out.append(Strings.repeat(spaces, " "));
                out.append(": ");
                out.append(ConsoleColors.YELLOW+lines.get(i));
                System.out.println(out);
            }
            System.out.println(ConsoleColors.YELLOW_BRIGHT+"---------------------------------");
        }
    }
}
