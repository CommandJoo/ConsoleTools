package de.jo.util.console;

public class ConsoleCodes {

    public static final char ESCAPE = '\033';
    public static final String CONTROL = ESCAPE + "[";

    /*
    *   ALL CONSOLE CODES THAT MODIFY THE CURSOR
    */
    public static final String RESET_CONSOLE = ESCAPE + "c";

    public static final String LINE_UP = CONTROL + "A";
    public static final String LINE_DOWN = CONTROL + "B";
    public static final String COLUMN_RIGHT = CONTROL + "C";
    public static final String COLUMN_LEFT = CONTROL + "D";
    public static final String BEGINNING_NEXT_LINE = CONTROL + "E";
    public static final String BEGINNING_PREV_LINE = CONTROL + "F";
    public static final String CURSOR_LINE_UP = ESCAPE + "M";

    public static String CURSOR_POSITION_SET(int x, int y) {
        return CONTROL + x + ";" + y + "H";
    }

    public static String CURSOR_POSITION_GET = CONTROL + "6n";

    public static String CURSOR_POSITION_SAVE = CONTROL + "s";
    public static String CURSOR_POSITION_LOAD = CONTROL + "u";

    /*
     *  ALL CONSOLE CODES THAT ERAS CHARACTERS
     */
    public static final String ERASE_FROM_CURSOR_TO_END = CONTROL+"0J";
    public static final String ERASE_FROM_CURSOR_TO_BEGINNING = CONTROL+"1J";
    public static final String ERASE_ALL = CONTROL+"2J";
    public static final String ERASE_FROM_CURSOR_TO_LINE_END = CONTROL+"0K";
    public static final String ERASE_FROM_CURSOR_TO_LINE_BEGINNING = CONTROL+"1K";
    public static final String ERASE_ALL_LINE = CONTROL+"2K";



}
