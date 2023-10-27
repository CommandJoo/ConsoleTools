package de.jo.util;

/**
 * @author Johannes Hans 27.10.2023
 * @Project ConsoleTools
 */
public class Strings {

    public String repeat(int r, String symbol) {
        StringBuilder s = new StringBuilder();
        for(int i = 0; i < r; i++) {
            s.append(symbol);
        }
        return s.toString();
    }

}
