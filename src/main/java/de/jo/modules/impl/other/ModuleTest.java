package de.jo.modules.impl.other;

import de.jo.modules.Module;
import de.jo.modules.ModuleInfo;
import de.jo.util.RawConsoleInput;
import org.apache.commons.codec.binary.CharSequenceUtils;
import org.apache.commons.codec.binary.StringUtils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * @author Johannes Hans 28.10.2023
 * @Project ConsoleTools
 */
@ModuleInfo(name = "test", description = "A Test Module to test the parsing of args")
public class ModuleTest implements Module {

    boolean test = true;

    @Override
    public void run(String... args) throws IOException, InterruptedException {
        test = true;
        while(test) {
            int key = RawConsoleInput.read(true);
            if(key == 27) {
                test = false;
                return;
            }

            String c = ""+(char)key;
            byte[] winbytes = c.getBytes("windows-1252");
            String wins = new String(winbytes, "windows-1252");

            System.out.println(StringUtils.newStringUtf8(c.getBytes()));

//            System.out.println(new String(c.getBytes("windows-1252"), StandardCharsets.UTF_8));

        }
    }
}
