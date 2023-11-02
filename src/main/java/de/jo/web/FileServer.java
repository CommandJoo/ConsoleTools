package de.jo.web;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import de.jo.util.ConsoleColors;
import de.jo.util.Strings;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author Johannes Hans 29.10.2023
 * @Project ConsoleTools
 */
public class FileServer {

    private int port;
    private String name, description;
    private HttpServer server;

    private List<File> files = new ArrayList<>();

    public FileServer(String name, int port) throws IOException {
        this(name, port, "");
    }

    public FileServer(String name, int port, String description) throws IOException {
        this.port = port;
        this.name = name;
        this.description = description;
    }

    /**
     * The addFile function adds a file to the server and prints a message indicating the file name.
     *
     * @param file The parameter "file" is of type File, which represents a file in the file system. It is the file that
     * you want to add to the server.
     */
    public void addFile(File file) {
        this.files.add(file);
        System.out.println(ConsoleColors.YELLOW_BRIGHT+"---------------------------------");
        System.out.println(ConsoleColors.YELLOW+"> "+ConsoleColors.YELLOW_BRIGHT+"Added File to Server: "+file.getName());
        System.out.println(ConsoleColors.YELLOW_BRIGHT+"---------------------------------");
    }

    /**
     * The function adds all files in a directory to a server and prints a message indicating the directory name.
     *
     * @param file The parameter "file" is of type File and represents the directory that you want to add to the server.
     */
    public void addDirectory(File file) {
        for(File subfile : Objects.requireNonNull(file.listFiles())) {
            if(!subfile.isDirectory()) this.files.add(subfile);
        }
        System.out.println(ConsoleColors.YELLOW_BRIGHT+"---------------------------------");
        System.out.println(ConsoleColors.YELLOW+"> "+ConsoleColors.YELLOW_BRIGHT+"Added Directory to Server: "+file.getName());
        System.out.println(ConsoleColors.YELLOW_BRIGHT+"---------------------------------");
    }

    /** The `FILE_TEMPLATE` is a string template used to generate HTML code for each file in the server. It contains
    placeholders `%FILE_NAME%` which will be replaced with the actual file name when generating the HTML code. This
    template is used in the `start()` method to generate the list of files in the server's index page.
     **/
    private static final String FILE_TEMPLATE = "<li>\n" +
            "                <div class=\"filename\">%FILE_NAME%</div>\n" +
            "                <button class=\"filelink\"><a href=\"%FILE_NAME%\">Download<br><p>Size: %FILE_SIZE%</p></a></button>\n" +
            "                <div class=\"filehash\">sha256: <button>%FILE_HASH%</button></div>\n" +
            "            </li>";

    /**
     * The start() function creates a server context for handling HTTP requests and starts the server on a specified port,
     * printing a message to the console.
     */
    public void start() throws IOException {
        this.server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/style.css", new HttpHandler() {
            @Override
            public void handle(HttpExchange ex) throws IOException {
                String css = Strings.lines(de.jo.util.Files.sysLines("style.css"));
                ex.sendResponseHeaders(200, css.length());
                ex.getResponseBody().write(css.getBytes());
                ex.getResponseBody().close();
            }
        });
        server.createContext("/", new HttpHandler() {
            @Override
            public void handle(HttpExchange ex) throws IOException {
                String html = Strings.lines(de.jo.util.Files.sysLines("fileserver.html"));
                html = html.replace("%TITLE%", name);
                html = html.replace("%DESCRIPTION%", description);

                StringBuilder filesList = new StringBuilder();
                for(File file : files) {
                    addFileEntry(file);
                    String hash = Strings.sha256(Strings.lines(de.jo.util.Files.lines(file)));
                    filesList.append(FILE_TEMPLATE.replace("%FILE_NAME%", file.getName()).replace("%FILE_SIZE%", ""+ de.jo.util.Files.sizeShortened((int) file.length())).replace("%FILE_HASH%", hash)).append("<br>");
                }
                html = html.replace("%FILES%", filesList);

                ex.sendResponseHeaders(200, html.length());
                ex.getResponseBody().write(html.getBytes());
                ex.getResponseBody().close();
            }
        });
        server.start();
        System.out.println(ConsoleColors.YELLOW+"> "+ConsoleColors.YELLOW_BRIGHT+"Started server: "+Strings.publicIP()+", on port: "+port);
    }

    /**
     * The function stops the server and prints a message indicating that the server has been stopped.
     */
    public void stop() {
        this.server.stop(0);
        this.server = null;
        System.out.println(ConsoleColors.YELLOW+"> "+ConsoleColors.YELLOW_BRIGHT+"Stopped server");

    }

    /**
     * The function `addFileEntry` creates a server context for a given file, allowing it to be accessed via HTTP.
     *
     * @param file The "file" parameter is an instance of the File class, which represents a file or directory on the file
     * system. In this case, it represents the file that will be added to the server's context.
     */
    private void addFileEntry(File file) {
        server.createContext("/"+file.getName(), new HttpHandler() {
            @Override
            public void handle(HttpExchange ex) throws IOException {
                byte[] data = Files.readAllBytes(file.toPath());
                ex.sendResponseHeaders(200, data.length);
                ex.getResponseBody().write(data);
                ex.getResponseBody().close();
            }
        });
    }

    public FileServer setName(String name) {
        this.name = name;
        return this;
    }

    public FileServer setDescription(String description) {
        this.description = description;
        return this;
    }

}
