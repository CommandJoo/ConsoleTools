package de.jo;

import org.apache.log4j.BasicConfigurator;
import org.mariuszgromada.math.mxparser.License;
import org.mariuszgromada.math.mxparser.Tutorial;

/**
 * @author Johannes Hans 27.10.2023
 * @Project ConsoleTools
 */
public class Main {

    public static void main(String[] args) throws Exception {

        BasicConfigurator.configure();
        License.iConfirmNonCommercialUse("Johannes Hans");
        new ConsoleTools(args);
    }

}
