package de.jo.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import de.jo.util.console.ConsoleColors;

import java.io.IOException;

/**
 * @author Johannes Hans 29.10.2023
 * @Project ConsoleTools
 */
public class CustomPrettyPrinter extends DefaultPrettyPrinter {

    public CustomPrettyPrinter() {
        super();
    }

    @Override
    public void writeRootValueSeparator(JsonGenerator jg) throws IOException {
        jg.writeRaw(ConsoleColors.RESET);
        super.writeRootValueSeparator(jg);
    }


    @Override
    public void writeStartObject(JsonGenerator jg) throws IOException {
        jg.writeRaw(ConsoleColors.GREEN);
        super.writeStartObject(jg);
        jg.writeRaw(ConsoleColors.CYAN);
    }

    @Override
    public void writeObjectFieldValueSeparator(JsonGenerator jg) throws IOException {
        jg.writeRaw(ConsoleColors.YELLOW);
        jg.writeRaw(": ");
        jg.writeRaw(ConsoleColors.BLUE);
    }

    @Override
    public void writeObjectEntrySeparator(JsonGenerator jg) throws IOException {
        jg.writeRaw(ConsoleColors.YELLOW);
        super.writeObjectEntrySeparator(jg);
        jg.writeRaw(ConsoleColors.CYAN);
    }

    @Override
    public void writeEndObject(JsonGenerator jg, int nrOfEntries) throws IOException {
        jg.writeRaw(ConsoleColors.GREEN);
        super.writeEndObject(jg, nrOfEntries);
        jg.writeRaw(ConsoleColors.RESET);
    }

    @Override
    public void writeStartArray(JsonGenerator jg) throws IOException {
        jg.writeRaw(ConsoleColors.YELLOW_BRIGHT);
        super.writeStartArray(jg);
        jg.writeRaw(ConsoleColors.BLUE);
    }

    @Override
    public void writeArrayValueSeparator(JsonGenerator jg) throws IOException {
        jg.writeRaw(ConsoleColors.YELLOW);
        super.writeArrayValueSeparator(jg);
        jg.writeRaw(ConsoleColors.BLUE);
    }

    @Override
    public void writeEndArray(JsonGenerator jg, int nrOfValues) throws IOException {
        jg.writeRaw(ConsoleColors.YELLOW_BRIGHT);
        super.writeEndArray(jg, nrOfValues);
        jg.writeRaw(ConsoleColors.RESET);
    }

    @Override
    public DefaultPrettyPrinter createInstance() {
        return new CustomPrettyPrinter();
    }
}
