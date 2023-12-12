package de.jo;

import org.apache.log4j.BasicConfigurator;
import org.mariuszgromada.math.mxparser.License;
import org.mariuszgromada.math.mxparser.Tutorial;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Johannes Hans 27.10.2023
 * @Project ConsoleTools
 */
public class Main {

    public static void main(String[] args) throws Exception {
        if(System.console() == null) { //RUN AS INSTALLER
        //TODO make it close itself and then launch a bat file that copys the jar to another directory
        String jarFile = new File(Main.class.getProtectionDomain().getCodeSource().getLocation()
                .toURI()).getPath();
        System.out.println(jarFile);
            Files.copy(Paths.get(jarFile), new FileOutputStream("C:/Coding/test.jar"));
            //JOptionPane.showMessageDialog(null, "Successfully Installed");
        }
        BasicConfigurator.configure();
        License.iConfirmNonCommercialUse("Johannes Hans");
        new ConsoleTools(args);
    }

}
