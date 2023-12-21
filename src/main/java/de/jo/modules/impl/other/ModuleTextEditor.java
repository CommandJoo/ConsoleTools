package de.jo.modules.impl.other;

import com.google.common.base.Charsets;
import de.jo.ConsoleTools;
import de.jo.modules.Module;
import de.jo.modules.ModuleInfo;
import de.jo.util.Files;
import de.jo.util.RawConsoleInput;
import de.jo.util.Strings;
import de.jo.util.console.ConsoleCodes;
import de.jo.util.console.ConsoleColors;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author Johannes Hans 21.12.2023
 * @Project ConsoleTools
 */
@ModuleInfo(name = "edit", description = "Opens a text editor")
public class ModuleTextEditor implements Module {

    public class Console {
        public ArrayList<String> lines;
        public int lastKey = 0;
        public String error = "";
        public int cursorX = 0, cursorY = 0;
        public int viewY = 0;

        public Console() {
            lines = new ArrayList<>();
            lines.add("");
        }

        public File file = new File("D:/edit.txt");

        public void print() {
            System.out.print(ConsoleCodes.RESET_CONSOLE);
            lines = new ArrayList<>(Files.lines(file));



            String text = "CURSOR POSITION: "+(cursorX+1)+":"+(cursorY+1)+", LINES: "+lines.size()+", LAST KEY: "+lastKey+""+(error.isEmpty() ? "" : ", ERROR: "+error);
            System.out.println(Strings.repeat(text.length(), "_"));


            ConsoleColors.ColorBuilder lineNumberBuilder = new ConsoleColors.ColorBuilder("");
            lineNumberBuilder.style(ConsoleColors.Styling.BOLD_ON);
            lineNumberBuilder.color(ConsoleColors.Color.YELLOW_BACKGROUND);
            if(cursorY-viewY > 21 && viewY+42< lines.size()) viewY++;
            if(cursorY-viewY < 21 && viewY > 0) viewY--;
            for(int i = viewY; i < viewY+42; i++) {
                if(i > lines.size()-1) {
                    System.out.println(Strings.repeat((""+lines.size()).length(), "~")+":");
                }else {
                    System.out.print(lineNumberBuilder.build()+lineNumber(i+1));
                    if(i == cursorY) {
                        System.out.println(lines.get(i).substring(0, cursorX)+"_"+(cursorX < lines.get(i).length() ? lines.get(i).substring(cursorX+1) : ""));
                    }else {
                        System.out.println(lines.get(i));
                    }
                }
            }

            ConsoleColors.ColorBuilder builder = new ConsoleColors.ColorBuilder(text);
            builder.style(ConsoleColors.Styling.BOLD_ON, ConsoleColors.Styling.UNDERLINE_ON);
            builder.color(ConsoleColors.Color.YELLOW);
            System.out.println(Strings.repeat(text.length(), "_"));
            System.out.println(builder.build());
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
            return lineNumber+Strings.repeat(longestLineNumber.length()-lineNumber.length(), " ")+": ";
        }

    }


    public Console console;
    public boolean running=true;

    public ModuleTextEditor() {
        this.console = new Console();
    }

    public File write = new File("D:/edit.txt");

    @Override
    public void run(String... args) throws Exception {
        System.out.print("\033c");
        while(running) {
            console.print();
            int key = RawConsoleInput.read(true);


            String currentLine = console.lines.isEmpty() ? " " : console.lines.get(console.cursorY);
            String infront = currentLine.substring(0, console.cursorX), after = currentLine.substring(console.cursorX);

            //13 = enter
            //8 = del
            //57416 = up
            //57424 = down
            //57419 = left
            //57421 = right
            //57427 = remove
            switch (key) {
                case 27:
                    running = false;
                    break;
                case 57416://arrow up
                    console.cursorY -= console.cursorY > 0 ? 1 : 0;
                    console.cursorX = 0;
                    break;
                case 57424://arrow down
                    console.cursorY += console.cursorY < console.lines.size()-1 ? 1 : 0;
                    console.cursorX = 0;
                    break;
                case 57419://arrow left
                    console.cursorX -= console.cursorX > 0 ? 1 : 0;
                    break;
                case 57421://arrow right
                    console.cursorX += console.cursorX < currentLine.length() ? 1 : 0;
                    break;
                case 8: //Del key
                    if(console.cursorX > 0) {
                        console.cursorX-=1;
                        String newInfront = infront.substring(0, infront.length()-1);
                        console.lines.set(console.cursorY, newInfront+after);
                    }else {
                        if(console.cursorY > 0) {//if nothing is infront of the cursor delete the line
                            console.lines.remove(console.cursorY);
                            console.cursorY-=1;

                            console.cursorX = console.lines.get(console.cursorY).length();//change cursor position to end of above line

                            if(!after.isEmpty()) {//add the rest after the cursor to the line above
                                console.lines.set(console.cursorY, console.lines.get(console.cursorY)+after);
                            }
                        }
                    }
                    break;
                case 57427: //remove
                    if(console.cursorX < currentLine.length()) {
                        String newAfter = after.substring(1);
                        console.lines.set(console.cursorY, infront+newAfter);
                    }else {
                        if(console.cursorY > 0) {//if nothing is after the cursor pull the next line up
                            if(console.cursorY != console.lines.size()-1) {
                                String nextLine = console.lines.get(console.cursorY+1);
                                console.lines.remove(console.cursorY+1);

                                console.lines.set(console.cursorY, currentLine+nextLine);
                            }
                        }
                    }
                    break;
                case 57423: //end key
                    console.cursorX = console.lines.get(console.cursorY).length();
                    break;
                case 57415://pos1 key
                    console.cursorX = 0;
                    break;
                case 57460: //CTRL + RIGHT ARROW
                    int afterPosition = after.indexOf(" ", 1);
                    console.cursorX = infront.length() + (afterPosition == -1 ? after.length() : afterPosition);
                    break;
                case 57459: //CTRL + LEFT ARROW
                    console.cursorX = (Math.max(infront.lastIndexOf(" "), 0));
                    break;
                case 13://enter
                    console.lines.set(console.cursorY, infront);
                    console.cursorY+=1;
                    console.lines.add(console.cursorY, after);
                    console.cursorX = 0;
                    break;
                default://add character
                   if(key < 500) {
                       console.lines.set(console.cursorY, infront+((char)key)+after);
                       console.cursorX += 1;
                   }else {
                        console.error = "Could not display key: "+key;
                   }
                    break;
            }
            console.lastKey = key;
            console.write();
            if(!running) break;
        }
        ConsoleTools.instance().logo();
        System.out.println(ConsoleColors.YELLOW_BRIGHT+"---------------------------------");
        System.out.println(ConsoleColors.YELLOW+"> "+ConsoleColors.YELLOW_BRIGHT+"Done with editing files!");
    }
}
