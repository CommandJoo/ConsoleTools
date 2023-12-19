package de.jo.math.commands;

import de.jo.math.Math;

/**
 * @author Johannes Hans 27.10.2023
 * @Project ConsoleTools
 */
public interface Command {

    public void run(Math math, String... args) throws Exception;

}
