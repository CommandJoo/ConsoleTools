package de.jo.math.commands.impl;

import de.jo.math.Math;
import de.jo.math.commands.Command;
import de.jo.math.commands.CommandInfo;
import de.jo.util.console.ConsoleColors;
import org.mariuszgromada.math.mxparser.Expression;

import java.awt.*;
import java.awt.datatransfer.StringSelection;

@CommandInfo(name = "e", description = "Parses an expression", syntax = "<Expression>")
public class CommandExpression implements Command {
    @Override
    public void run(Math math, String... args) throws Exception {
        Expression expression = new Expression(args[0]);
        math.functions().values().forEach(expression::addDefinitions);
        math.constants().values().forEach(expression::addDefinitions);
        expression.enableImpliedMultiplicationMode();
        if (math.isVerbose) expression.setVerboseMode();

        Object obj = expression.calculate();
        copy(obj.toString());
        System.out.println(ConsoleColors.GREEN + "> " + obj.toString());
    }

    public void copy(String result) {
        StringSelection selection = new StringSelection(result);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, selection);
    }
}
