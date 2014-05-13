package org.ampathkenya;

import org.ampathkenya.screen.Screen;

import java.util.HashMap;

public class CSVToSQLConverter {

    private static final String PATH_ARG = "p";
    private static final String CONFIG_FILE_PATH = "c" ;
    private static final String CSV_FILES_PATH_NOT_SPECIFIED_ERROR = "Error. Path to csv files not specified. Please " +
            "use -p  to specify path to csv file or folder with csv files"  ;
    private static final String CONFIG_FILE_PATH_NOT_SPECIFIED_ERROR = "Error. Path to config file not specified. Please " +
            "use -c to specify the path to the config file" ;

    private static Screen screen ;
    private static HashMap<String, String> programParameters = new HashMap<String, String>() ;

    public static void main(String [] args) {

        screen = new Screen() ;
        programParameters = loadProgramParameters() ;

        if(!programParameters.containsKey(PATH_ARG)) {
            screen.displayText(CSV_FILES_PATH_NOT_SPECIFIED_ERROR) ;
            System.exit(0);
        }

        if (!programParameters.containsKey(CONFIG_FILE_PATH)) {
            screen.displayText(CONFIG_FILE_PATH_NOT_SPECIFIED_ERROR);
            System.exit(0);
        }

        processCSVFiles(programParameters) ;
    }

    private static HashMap<String, String> loadProgramParameters() {
        return new HashMap<String, String>();
    }

    private static void processCSVFiles(HashMap<String, String> programParameters) {

    }
}
