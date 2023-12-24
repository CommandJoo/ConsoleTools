package de.jo.util.console;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Arrays;

public class ConsoleColors {

    public static final char ESCAPE = '\033';
    public static final String CONTROL = ESCAPE + "[";

    // Reset
    public static final String RESET = CONTROL+"0m";  // Text Reset

    public static final char COLOR_END = 'm';
    public enum Styling {
        BOLD_ON("1"), BOLD_OFF("22"),
        DIM_ON("2"), DIM_OFF("22"),
        ITALIC_ON("3"), ITALIC_OFF("23"),
        UNDERLINE_ON("4"), UNDERLINE_OFF("24"),
        BLINKING_ON("5"), BLINKING_OFF("25"),
        INVERSE_ON("7"), INVERSE_OFF("27"),
        HIDDEN_ON("8"), HIDDEN_OFF("28"),
        STRIKE_THROUGH_ON("9"), STRIKE_THROUGH_OFF("29");

        private final String sequence;

        private Styling(String sequence) {
            this.sequence = sequence;
        }

        public String sequence() {
            return sequence;
        }
    }
    public enum Color {
        BLACK(30), BLACK_BACKGROUND(40),
        RED(31), RED_BACKGROUND(41),
        GREEN(32), GREEN_BACKGROUND(42),
        YELLOW(33), YELLOW_BACKGROUND(43),
        BLUE(34), BLUE_BACKGROUND(44),
        MAGENTA(35), MAGENTA_BACKGROUND(45),
        CYAN(36), CYAN_BACKGROUND(46),
        WHITE(37), WHITE_BACKGROUND(47),
        DEFAULT(39), DEFAULT_BACKGROUND(49),
        RESET(0), RESET_BACKGROUND(0)
        ;

        private final int code;

        private Color(int code) {
            this.code = code;
        }

        public String code() {
            return ""+code;
        }
    }

    public static class ColorBuilder {

        private final String text;
        private ArrayList<String> styles = new ArrayList<>();
        private ArrayList<String> colors = new ArrayList<>();

        public ColorBuilder(String text) {
            this.text = text;
        }

        public String text() {
            return text;
        }

        public ColorBuilder style(Styling... styles) {
            for(Styling styling : styles) {
                if(!this.styles.contains(styling.sequence())) this.styles.add(styling.sequence());
            }
            return this;
        }

        public ColorBuilder color(Color... colors) {
            for(Color color : colors) {
                if(!this.colors.contains(color.code())) this.colors.add(color.code());
            };
            return this;
        }
        
        public String build() {
            StringBuilder text = new StringBuilder(CONTROL);
            for(int i = 0; i < colors.size(); i++) {
                text.append(colors.get(i));
                if(i < colors.size()-1) text.append(";");
            }
            if(!styles.isEmpty()) text.append(";");
            for(int i = 0; i < styles.size(); i++) {
                text.append(styles.get(i));
                if(i < styles.size()-1) text.append(";");
            }
            return text.append(COLOR_END).append(this.text()).toString();
        }

    }

    // Regular Colors
    public static final String BLACK = CONTROL+"0;30m";   // BLACK
    public static final String RED = CONTROL+"0;31m";     // RED
    public static final String GREEN = CONTROL+"0;32m";   // GREEN
    public static final String YELLOW = CONTROL+"0;33m";  // YELLOW
    public static final String BLUE = CONTROL+"0;34m";    // BLUE
    public static final String PURPLE = CONTROL+"0;35m";  // PURPLE
    public static final String CYAN = CONTROL+"0;36m";    // CYAN
    public static final String WHITE = CONTROL+"0;37m";   // WHITE

    // Bold
    public static final String BLACK_BOLD = CONTROL+"1;30m";  // BLACK
    public static final String RED_BOLD = CONTROL+"1;31m";    // RED
    public static final String GREEN_BOLD = CONTROL+"1;32m";  // GREEN
    public static final String YELLOW_BOLD = CONTROL+"1;33m"; // YELLOW
    public static final String BLUE_BOLD = CONTROL+"1;34m";   // BLUE
    public static final String PURPLE_BOLD = CONTROL+"1;35m"; // PURPLE
    public static final String CYAN_BOLD = CONTROL+"1;36m";   // CYAN
    public static final String WHITE_BOLD = CONTROL+"1;37m";  // WHITE

    // Underline
    public static final String BLACK_UNDERLINED = CONTROL+"4;30m";  // BLACK
    public static final String RED_UNDERLINED = CONTROL+"4;31m";    // RED
    public static final String GREEN_UNDERLINED = CONTROL+"4;32m";  // GREEN
    public static final String YELLOW_UNDERLINED = CONTROL+"4;33m"; // YELLOW
    public static final String BLUE_UNDERLINED = CONTROL+"4;34m";   // BLUE
    public static final String PURPLE_UNDERLINED = CONTROL+"4;35m"; // PURPLE
    public static final String CYAN_UNDERLINED = CONTROL+"4;36m";   // CYAN
    public static final String WHITE_UNDERLINED = CONTROL+"4;37m";  // WHITE

    // Background
    public static final String BLACK_BACKGROUND = CONTROL+"40m";  // BLACK
    public static final String RED_BACKGROUND = CONTROL+"41m";    // RED
    public static final String GREEN_BACKGROUND = CONTROL+"42m";  // GREEN
    public static final String YELLOW_BACKGROUND = CONTROL+"43m"; // YELLOW
    public static final String BLUE_BACKGROUND = CONTROL+"44m";   // BLUE
    public static final String PURPLE_BACKGROUND = CONTROL+"45m"; // PURPLE
    public static final String CYAN_BACKGROUND = CONTROL+"46m";   // CYAN
    public static final String WHITE_BACKGROUND = CONTROL+"47m";  // WHITE

    // High Intensity
    public static final String BLACK_BRIGHT = CONTROL+"0;90m";  // BLACK
    public static final String RED_BRIGHT = CONTROL+"0;91m";    // RED
    public static final String GREEN_BRIGHT = CONTROL+"0;92m";  // GREEN
    public static final String YELLOW_BRIGHT = CONTROL+"0;93m"; // YELLOW
    public static final String BLUE_BRIGHT = CONTROL+"0;94m";   // BLUE
    public static final String PURPLE_BRIGHT = CONTROL+"0;95m"; // PURPLE
    public static final String CYAN_BRIGHT = CONTROL+"0;96m";   // CYAN
    public static final String WHITE_BRIGHT = CONTROL+"0;97m";  // WHITE

    // Bold High Intensity
    public static final String BLACK_BOLD_BRIGHT = CONTROL+"1;90m"; // BLACK
    public static final String RED_BOLD_BRIGHT = CONTROL+"1;91m";   // RED
    public static final String GREEN_BOLD_BRIGHT = CONTROL+"1;92m"; // GREEN
    public static final String YELLOW_BOLD_BRIGHT = CONTROL+"1;93m";// YELLOW
    public static final String BLUE_BOLD_BRIGHT = CONTROL+"1;94m";  // BLUE
    public static final String PURPLE_BOLD_BRIGHT = CONTROL+"1;95m";// PURPLE
    public static final String CYAN_BOLD_BRIGHT = CONTROL+"1;96m";  // CYAN
    public static final String WHITE_BOLD_BRIGHT = CONTROL+"1;97m"; // WHITE

    // High Intensity backgrounds
    public static final String BLACK_BACKGROUND_BRIGHT = CONTROL+"0;100m";// BLACK
    public static final String RED_BACKGROUND_BRIGHT = CONTROL+"0;101m";// RED
    public static final String GREEN_BACKGROUND_BRIGHT = CONTROL+"0;102m";// GREEN
    public static final String YELLOW_BACKGROUND_BRIGHT = CONTROL+"0;103m";// YELLOW
    public static final String BLUE_BACKGROUND_BRIGHT = CONTROL+"0;104m";// BLUE
    public static final String PURPLE_BACKGROUND_BRIGHT = CONTROL+"0;105m"; // PURPLE
    public static final String CYAN_BACKGROUND_BRIGHT = CONTROL+"0;106m";  // CYAN
    public static final String WHITE_BACKGROUND_BRIGHT = CONTROL+"0;107m";   // WHITE

}