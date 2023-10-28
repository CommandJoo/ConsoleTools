package de.jo.modules.impl.url;

import de.jo.modules.Module;
import de.jo.modules.ModuleInfo;
import de.jo.util.ConsoleColors;
import de.jo.util.Files;
import de.jo.util.Strings;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * @author Johannes Hans 27.10.2023
 * @Project ConsoleTools
 */
@ModuleInfo(name = "uconcatenate", description = "Prints the contents of a url to the console, and optionally writes it to a file", aliases = "ucat", syntax = "<URL> <File(Optional)>")
public class ModuleURLConcatenate implements Module {
    @Override
    public void run(String... args) throws IOException {
        if(args.length == 1) {
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
        }else if(args.length == 2) {
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
                Files.write(lines, args[1]);
                System.out.println(ConsoleColors.YELLOW+"Saved contents of URl to "+args[1]);
                System.out.println(ConsoleColors.YELLOW_BRIGHT+"---------------------------------");
        }
    }
}
