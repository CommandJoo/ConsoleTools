package de.jo.util;

import de.jo.ConsoleTools;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Johannes Hans 27.10.2023
 * @Project ConsoleTools
 */
public class Files {

    public static List<String> lines(InputStream is) {
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader bfr = new BufferedReader(isr);

        List<String> lines = new ArrayList<>();
        String line = "";
        try {
            while((line = bfr.readLine()) != null) {
                lines.add(line);
            }
            bfr.close();
            isr.close();
        } catch(Exception ex) {}
        return lines;
    }
    public static List<String> lines(File file) {
        return lines(file.getAbsolutePath());
    }
    public static List<String> lines(String file) {
        File f = new File(file);
        if(!f.exists()) return new ArrayList<>();
        try {
            return lines(java.nio.file.Files.newInputStream(Paths.get(file)));
        } catch(Exception ex) {
            ex.printStackTrace();
            return new ArrayList<>();
        }
    }
    public static List<String> sysLines(String path) {
        return lines(ClassLoader.getSystemClassLoader().getResourceAsStream(path));
    }

    public static boolean write(List<String> lines, File file) {
        try {
            if(!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            Writer writer = new BufferedWriter(new OutputStreamWriter(java.nio.file.Files.newOutputStream(file.toPath()), StandardCharsets.UTF_8));
            for(String s : lines) {
                writer.write(s.endsWith("\n") ? s : s+"\n");
            }
            writer.close();
        } catch(Exception ex) {
            Strings.error("Couldn't write to file");
        }
        return false;
    }
    public static boolean write(List<String> lines, String file) {
        return write(lines, new File(file));
    }

    public static String parseFile(String arg) {
        arg = arg.replace("$", ConsoleTools.instance().currentDirectory.getAbsolutePath());
        return arg;
    }

    public static boolean isValidFileName(String name) {
        final Character[] INVALID_WINDOWS_SPECIFIC_CHARS = {'"', '*', '<', '>', '?', '|'};

        for(Character charr : INVALID_WINDOWS_SPECIFIC_CHARS) {
            if(name.contains(""+charr)) return false;
        }
        return true;
    }

    public static String sizeShortened(int sizeInBytes) {
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
