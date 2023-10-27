package de.jo.options;

import joptsimple.OptionParser;
import joptsimple.OptionSet;

/**
 * @author Johannes Hans 27.10.2023
 * @Project ConsoleTools
 */
public class Options {

    private final OptionParser parser;

    public Options() {
        this.parser = new OptionParser();
    }

    public void loadOption(Option option) {
        if(option.required()) {
            this.parser.accepts(option.name(), option.description()).withRequiredArg().ofType(option.type());
        } else {
            this.parser.accepts(option.name(), option.description());
        }
    }

    public OptionSet build(String[] args) {
        return parser.parse(args);
    }
}
