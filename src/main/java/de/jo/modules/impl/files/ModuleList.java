package de.jo.modules.impl.files;

import de.jo.modules.Module;
import de.jo.modules.ModuleInfo;
import de.jo.util.ConsoleColors;
import de.jo.util.Files;
import de.jo.util.Strings;

import java.io.File;
import java.util.Objects;

/**
 * @author Johannes Hans 28.10.2023
 * @Project ConsoleTools
 */
@ModuleInfo(name = "list", description = "Lists the contents of a directory", aliases = "ls", syntax = "<Path>")
public class ModuleList implements Module {
    @Override
    public void run(String... args) throws Exception {
        if(args.length == 1) {
            File dir = new File(Files.parseFile(args[0]));
            if(!dir.exists() || !dir.isDirectory()) {
                Strings.error("File needs to be a directory");
                Strings.moduleError(this, 24);
                return;
            }
            int longestLength = dir.getCanonicalPath().length();
            for(String s : Objects.requireNonNull(dir.list())) {
                if(s.length() > longestLength) longestLength=s.length();
            }

            System.out.println(ConsoleColors.YELLOW_BRIGHT+"L___ "+ConsoleColors.YELLOW+dir.getCanonicalPath()+Strings.repeat((longestLength-dir.getCanonicalPath().length())+5, " ")+ConsoleColors.YELLOW_BRIGHT+"   | "+ConsoleColors.RED_BRIGHT+sizeShortened(size(dir)));

            for(File file : Objects.requireNonNull(dir.listFiles())) {
                String filesize = sizeShortened((int) file.length());
                int spaces = longestLength-file.getName().length()+5;
                System.out.println(ConsoleColors.YELLOW_BRIGHT+"   L___ "+ConsoleColors.YELLOW+file.getName()+Strings.repeat(spaces, " ")+ConsoleColors.YELLOW_BRIGHT+"| "+ConsoleColors.RED_BRIGHT+filesize);
            }
        }else {
            Strings.error("Invalid usage");
        }
    }

    public int size(File dir) {
        int size = 0;
        for(File file : Objects.requireNonNull(dir.listFiles())) {
            size+=file.length();
        }
        return size;
    }

    public String sizeShortened(int sizeInBytes) {
        if(sizeInBytes < 1000) {
            return (sizeInBytes)+"b";
        }
        if(sizeInBytes < 1000000) {
            return (sizeInBytes/1000F)+"Kb";
        }
        if(sizeInBytes < 1000000000) {
            return (sizeInBytes/1000000F)+"Mb";
        }else{
            return (sizeInBytes/1000000000F)+"Gb";
        }
    }

}
