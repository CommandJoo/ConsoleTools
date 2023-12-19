package de.jo.math;

import org.mariuszgromada.math.mxparser.Constant;
import org.mariuszgromada.math.mxparser.Function;

import java.util.HashMap;

public class Math {

    private final HashMap<String, Function> functions;
    private final HashMap<String, Constant> constants;

    public boolean isCopy=false, isVerbose=false, isMath=true;

    public Math() {
        this.functions = new HashMap<>();
        this.constants = new HashMap<>();
    }
    public HashMap<String, Function> functions() {
        return functions;
    }

    public HashMap<String, Constant> constants() {
        return constants;
    }

}
