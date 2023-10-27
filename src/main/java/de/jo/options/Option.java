package de.jo.options;

/**
 * @author Johannes Hans 27.10.2023
 * @Project ConsoleTools
 */
public class Option {

    private final String name;
    private String description = "";
    private boolean required = false;
    private Class<?> type = Object.class;

    private Option(String name) {
        this.name = name;
    }

    public Option(String name, boolean required, Class<?> type) {
        this.name = name;
        this.required = required;
        this.type = type;
    }

    public static Option builder(String name) {
        return new Option(name);
    }

    public Option description(String description) {
        this.description = description;
        return this;
    }

    public Option required(boolean required) {
        this.required = required;
        return this;
    }

    public Option type(Class<?> type) {
        this.type = type;
        return this;
    }

    public String name() {
        return name;
    }

    public String description() {
        return description;
    }

    public boolean required() {
        return required;
    }

    public Class<?> type() {
        return type;
    }
}
