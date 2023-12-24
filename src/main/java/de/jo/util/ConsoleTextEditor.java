package de.jo.util;

import de.jo.util.console.ConsoleCodes;
import de.jo.util.console.ConsoleColors;

import java.io.File;
import java.util.ArrayList;

/**
 * @author Johannes Hans 24.12.2023
 * @Project ConsoleTools
 */
public class ConsoleTextEditor {
    public ArrayList<String> lines;
    public int lastKey = 0;
    public String error = "";
    public int cursorX = 0, cursorY = 0;
    public int viewY = 0;

    public ConsoleTextEditor() {
        lines = new ArrayList<>();
        lines.add("");
    }

    public File file = new File("D:/edit.txt");

    public String info() {
        return "CURSOR POSITION: "+(cursorX+1)+":"+(cursorY+1)+", LINES: "+lines.size()+", LAST KEY: "+lastKey+""+(error.isEmpty() ? "" : ", ERROR: "+error);
    }

    public String infoDisplay(boolean fileLonger, int difference) {
        ConsoleColors.ColorBuilder builder = new ConsoleColors.ColorBuilder(info()+(!fileLonger ? "" : Strings.repeat(difference, " ")));
        builder = builder.style(ConsoleColors.Styling.INVERSE_ON);
        builder = builder.color(ConsoleColors.Color.DEFAULT);
        return builder.build();
    }

    public void print() {
        System.out.print(ConsoleCodes.RESET_CONSOLE);
        lines = new ArrayList<>(Files.lines(file));

        String filePath = "File: "+this.file.getAbsolutePath();
        int difference = Math.abs(filePath.length() - info().length());
        boolean fileLonger = filePath.length() > info().length();

        System.out.println(new ConsoleColors.ColorBuilder(filePath+(fileLonger ? "" : Strings.repeat(difference, " "))).style(ConsoleColors.Styling.INVERSE_ON).build());


        if(cursorY-viewY > 21 && viewY+42< lines.size()) viewY++;
        if(cursorY-viewY < 21 && viewY > 0) viewY--;
        for(int i = viewY; i < viewY+42; i++) {
            if(i > lines.size()-1) {
                System.out.println(emptyLine());
            }else {
                System.out.print(lineNumber(i+1));
                if(i == cursorY) {
                    String line = lines.get(i);
                    System.out.print(line.substring(0, cursorX));
                    System.out.print(new ConsoleColors.ColorBuilder(cursorX < line.length() ? line.charAt(cursorX)+"" : " ").style(ConsoleColors.Styling.INVERSE_ON, ConsoleColors.Styling.BLINKING_ON).build()+ConsoleColors.RESET);
                    System.out.print(cursorX < line.length() ? line.substring(cursorX+1) : "");
                    System.out.println();
                }else {
                    System.out.println(lines.get(i));
                }
            }
        }

        System.out.println(infoDisplay(fileLonger, difference));

    }

    public void write() {
        Files.write(lines, file);
    }

    public String lineNumber(int i) {
        String lineNumber = ""+i;
        String longestLineNumber = ""+lines.size();
        //1   :
        //2   :
        //3   :
        //..  :
        //... :
        //1034:

        ConsoleColors.ColorBuilder lineNumberBuilder = new ConsoleColors.ColorBuilder(lineNumber+Strings.repeat(longestLineNumber.length()-lineNumber.length(), " ")+":");
        lineNumberBuilder.style(ConsoleColors.Styling.BOLD_ON, ConsoleColors.Styling.INVERSE_ON);
        lineNumberBuilder.color(i-1 == cursorY ? ConsoleColors.Color.BLACK_BACKGROUND : ConsoleColors.Color.DEFAULT_BACKGROUND, i-1 == cursorY ? ConsoleColors.Color.WHITE : ConsoleColors.Color.DEFAULT);

        return lineNumberBuilder.build()+ConsoleColors.RESET+" ";
    }

    public String emptyLine() {
        String text = Strings.repeat((""+lines.size()).length(), "~")+":";

        ConsoleColors.ColorBuilder lineNumberBuilder = new ConsoleColors.ColorBuilder(text);
        lineNumberBuilder.style(ConsoleColors.Styling.BOLD_ON, ConsoleColors.Styling.INVERSE_ON);
        lineNumberBuilder.color(ConsoleColors.Color.DEFAULT, ConsoleColors.Color.DEFAULT_BACKGROUND);

        return lineNumberBuilder.build()+ConsoleColors.RESET+" ";
    }

}
