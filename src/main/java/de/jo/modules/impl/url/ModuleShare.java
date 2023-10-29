package de.jo.modules.impl.url;

import de.jo.modules.Module;
import de.jo.modules.ModuleInfo;
import de.jo.util.ConsoleColors;
import de.jo.util.Files;
import de.jo.util.Strings;
import de.jo.web.FileServer;

import java.io.File;

/**
 * @author Johannes Hans 29.10.2023
 * @Project ConsoleTools
 */
@ModuleInfo(name = "fileshare", description = "Shares files to an HTTP server for other networks", aliases = {"fileserver", "fshare", "fs"}, syntax = "start <Name> <Port> | <share> <--f|--d> <File|Path> | <stop> | description <Description>")
public class ModuleShare implements Module {

    private FileServer fileServer;

    @Override
    public void run(String... args) throws Exception {
        if (args.length == 1) {
            if (args[0].equals("stop")) {
                if (fileServer == null) {
                    System.out.println(ConsoleColors.YELLOW + "> " + ConsoleColors.YELLOW_BRIGHT + "Server not found!");
                    return;
                } else {
                    fileServer.stop();
                    fileServer = null;
                    return;
                }
            }
        } else if (args.length == 2) {
            if(args[0].equals("description")) {
                fileServer.stop();
                fileServer.setDescription(args[1]);
                fileServer.start();
            }
        } else if (args.length == 3) {
            if (args[0].equals("start")) {
                String name = args[1];
                Integer port = Integer.parseInt(args[2]);
                if (fileServer != null) {
                    fileServer.stop();
                    fileServer = null;
                }
                fileServer = new FileServer(name, port, "You can download the shared files by clicking the link down below");
                fileServer.start();
            } else if (args[0].equals("share")) {
                if (args[1].equals("--f")) {
                    File file = new File(Files.parseFile(args[2]));

                    if (!file.exists()) {
                        Strings.error("File: \"" + file.getCanonicalPath() + "\" not found");
                        Strings.moduleError(this, 24);
                        return;
                    }

                    fileServer.stop();

                    fileServer.addFile(file);
                    fileServer.start();
                } else if (args[1].equals("--d")) {
                    File file = new File(Files.parseFile(args[2]));

                    if (!file.exists() || !file.isDirectory()) {
                        Strings.error("File: \"" + file.getCanonicalPath() + "\" must exist and be a directory");
                        Strings.moduleError(this, 24);
                        return;
                    }
                    fileServer.stop();

                    fileServer.addDirectory(file);
                    fileServer.start();
                } else {
                    Strings.error("Invalid usage");
                }
            } else {
                Strings.error("Invalid usage");
            }
        } else {
            Strings.error("Invalid usage");
        }
    }
}
