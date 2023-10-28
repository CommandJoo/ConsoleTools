package de.jo.util;

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
                file.mkdirs();
                file.createNewFile();
            }
            Writer writer = new BufferedWriter(new OutputStreamWriter(java.nio.file.Files.newOutputStream(file.toPath()), StandardCharsets.UTF_8));
            for(String s : lines) {
                writer.write(s);
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

}
