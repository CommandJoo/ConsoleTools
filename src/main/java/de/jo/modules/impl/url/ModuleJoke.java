package de.jo.modules.impl.url;

import com.google.gson.*;
import de.jo.modules.Module;
import de.jo.modules.ModuleInfo;
import de.jo.util.ConsoleColors;
import de.jo.util.Files;
import de.jo.util.Strings;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * @author Johannes Hans 29.10.2023
 * @Project ConsoleTools
 */
@ModuleInfo(name = "joke", description = "Prints a joke", syntax = "<Any|Programming,Misc,Dark,Pun,Spooky,Christmas(Category)> <fr|cs|de|en|es|pt(Language)>")
public class ModuleJoke implements Module {

    public static final String API_URL = "https://v2.jokeapi.dev/joke/";

    @Override
    public void run(String... args) throws Exception {
        if(args.length == 2) {
            String category = args[0];
            String lang = args[1];

            String urlString = API_URL+""+category+"?lang="+lang;
            URL url = new URL(urlString);
            InputStream is = url.openStream();
            if(is == null) {
                Strings.error("URL: \""+urlString+"\" not found");
                Strings.moduleError(this, 34);
                return;
            }
            List<String> lines = Files.lines(is);
            String response = Strings.lines(lines);
            JsonObject el = JsonParser.parseString(response).getAsJsonObject();
            if(!el.get("error").getAsBoolean()) {
                if(el.get("joke") != null) {
                    System.out.println(ConsoleColors.YELLOW+"> "+ConsoleColors.YELLOW_BRIGHT+el.get("joke").getAsString());
                }else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                System.out.println(ConsoleColors.YELLOW+"> "+ConsoleColors.YELLOW_BRIGHT+el.get("setup").getAsString());
                                Thread.sleep(200);
                                System.out.println(ConsoleColors.YELLOW+".");
                                Thread.sleep(200);
                                System.out.println(ConsoleColors.YELLOW+"..");
                                Thread.sleep(200);
                                System.out.println(ConsoleColors.YELLOW+"...");
                                Thread.sleep(200);
                                System.out.println(ConsoleColors.YELLOW+"....");
                                Thread.sleep(200);
                                System.out.println(ConsoleColors.YELLOW+"> "+ConsoleColors.YELLOW_BRIGHT+el.get("delivery").getAsString());
                            } catch(Exception ex) {}
                        }
                    }).start();
                }
            }else{
                Strings.error("No joke found");
            }

        }else{
            Strings.error("Invalid usage");
        }
    }
}
