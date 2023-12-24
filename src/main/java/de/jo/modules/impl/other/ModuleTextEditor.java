package de.jo.modules.impl.other;

import de.jo.ConsoleTools;
import de.jo.modules.Module;
import de.jo.modules.ModuleInfo;
import de.jo.util.ConsoleTextEditor;
import de.jo.util.RawConsoleInput;
import de.jo.util.console.ConsoleColors;

import java.io.File;
import java.nio.charset.Charset;

/**
 * @author Johannes Hans 21.12.2023
 * @Project ConsoleTools
 */
@ModuleInfo(name = "edit", description = "Opens a text editor", syntax = "<File>")
public class ModuleTextEditor implements Module {

    public ConsoleTextEditor console;
    public boolean running=true;

    public ModuleTextEditor() {
        this.console = new ConsoleTextEditor();
    }

    @Override
    public void run(String... args) throws Exception {
        this.console.file = new File(args[0]);
        System.out.print("\033c");
        while(running) {
            console.print();
            int key = RawConsoleInput.read(true);
            String character = ""+(char)key;

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
                    if(!console.lines.isEmpty()) {
                        console.lines.set(console.cursorY, infront);
                        console.cursorY+=1;
                        console.lines.add(console.cursorY, after);
                        console.cursorX = 0;
                    }else {
                        console.lines.add("");
                    }
                    break;
                default://add character
                   if(Charset.forName("US-ASCII").newEncoder().canEncode(character)) {
                       console.lines.set(console.cursorY, infront+(character)+after);
                       console.cursorX += 1;
                   }else {
                        console.error = "Could not display: "+character;
                   }
                    break;
            }
            console.lastKey = key;
            console.write();
            if(!running) break;
        }

        try {
            ConsoleTools.instance().logo();
        } catch(Exception ex) {

        }
        System.out.println(ConsoleColors.YELLOW_BRIGHT+"---------------------------------");
        System.out.println(ConsoleColors.YELLOW+"> "+ConsoleColors.YELLOW_BRIGHT+"Done with editing files!");
    }

}
