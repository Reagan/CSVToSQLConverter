package org.ampathkenya;

import org.ampathkenya.screen.Screen;
import org.ampathkenya.utils.FileUtils;
import org.ampathkenya.utils.argumentprocessor.ArgumentProcessor;
import org.ampathkenya.utils.argumentprocessor.exception.UndefinedFlagException;
import org.ampathkenya.utils.argumentprocessor.exception.UndefinedValueException;
import org.ampathkenya.utils.argumentprocessor.exception.UnsupportedFlagException;
import org.ampathkenya.utils.configparser.ConfigFileParser;
import org.ampathkenya.utils.configparser.TableConfig;
import org.ampathkenya.utils.csvparser.CSVParser;

import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class CSVToSQLConverter {

    private static final String PATH_ARG = "p";
    private static final String CONFIG_FILE_PATH = "c" ;
    private static final String CSV_FILES_PATH_NOT_SPECIFIED_ERROR = "Error. Path to csv files not specified. Please " +
            "use -p  to specify path to csv file or folder with csv files"  ;
    private static final String CONFIG_FILE_PATH_NOT_SPECIFIED_ERROR = "Error. Path to config file not specified. Please " +
            "use -c to specify the path to the config file" ;
    private static final String FILE_OR_FOLDER_NOT_FOUND_EXCEPTION = "Error: Path to csv files not valid. Please re-run the " +
            "program using a valid path" ;
    private static final String CSV_EXTENSION = ".csv" ;


    private CSVParser csvParser ;
    private static ConfigFileParser configFileParser ;
    private static Screen screen ;
    private static HashMap<String, String> programParameters = new HashMap<String, String>() ;
    private static ArgumentProcessor argumentProcessor = new ArgumentProcessor() ;

    public static void main(String [] args) {

        screen = new Screen() ;
        programParameters = loadProgramParameters(args) ;

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

    private static HashMap<String, String> loadProgramParameters(String [] args) {
        HashMap<String, String> argsAndParams = new HashMap<String, String>() ;
        String argsString = concatenate(args) ;

        try {
            argsAndParams = argumentProcessor.extractArgsAndProperties(argsString) ;
        } catch (UndefinedValueException e) {
            screen.displayText(e.getMessage());
        } catch (UndefinedFlagException e) {
            screen.displayText(e.getMessage());
        } catch (UnsupportedFlagException e) {
            screen.displayText(e.getMessage());
        }
        return  argsAndParams ;
    }

    private static String concatenate(String[] args) {
        String argsString = "" ;
        final String SEPARATOR = " " ;

        for(int argsCounter = 0 ;
                argsCounter < args.length ;
                argsCounter++) {

            if (argsCounter == args.length - 1) {
                argsString += args[argsCounter] ;
            } else {
                argsString += args[argsCounter] + SEPARATOR ;
            }
        }
        return argsString ;
    }

    private static void processCSVFiles(HashMap<String, String> programParameters) {
        String pathToCSVFiles = GetParameters.getPath(programParameters) ;
        ArrayList<TableConfig> tableConfigs = loadTableConfigs(GetParameters.getSQLDumpFilePathOutput(programParameters)) ;

        String[] filePaths = loadCSVFiles(pathToCSVFiles) ;

        for(String filePath: filePaths){
            TableConfig currTableConfig = getConfigForCSVFile(filePath, tableConfigs) ;

            if(currTableConfig != null) {
                processCSV(filePath, currTableConfig) ;
            }
        }
    }

    private static String[] loadCSVFiles(String pathToCSVFiles) {
        String [] csvFilePaths = null ;

        try {
            if(checkPath(pathToCSVFiles)) {
                csvFilePaths = getCSVFileNames(pathToCSVFiles) ;
            } else {
                throw new Exception(FILE_OR_FOLDER_NOT_FOUND_EXCEPTION) ;
            }
        } catch (Exception e) {
            screen.displayText(e.getMessage());
            System.exit(0);
        }

        return csvFilePaths ;
    }

    private static TableConfig getConfigForCSVFile(String filePath, ArrayList<TableConfig> tableConfigs) {
        TableConfig tableConfig = null;

        String table = filePath.substring(0, filePath.indexOf(CSV_EXTENSION)) ;
        Iterator<TableConfig> tableConfigIterator = tableConfigs.iterator();

        while(tableConfigIterator.hasNext()) {
            TableConfig currTableConfig = tableConfigIterator.next() ;
            if(currTableConfig.getTableName().equals(table)) {
                tableConfig = currTableConfig ;
                break;
            }
        }
        return tableConfig ;
    }

    private static boolean checkPath(String pathToCSVFiles) {
        if(isDir(pathToCSVFiles)) {
            return containsCSVFile(pathToCSVFiles) ;
        } else if (isFile(pathToCSVFiles)) {
            return isCSVFile(pathToCSVFiles) ;
        }
        return false ;
    }

    private static boolean isDir(String pathToCSVFiles) {
        if(isValidPath(pathToCSVFiles)) {
            return new File(pathToCSVFiles).isDirectory() ;
        }
        return false ;
    }

    private static boolean isFile(String pathToCSVFiles) {
        if(isValidPath(pathToCSVFiles)) {
            return new File(pathToCSVFiles).isFile() ;
        }
        return false ;
    }

    private static boolean isValidPath(String path) {
        return new File(path).exists() ;
    }

    private static boolean containsCSVFile(String dirPath) {
        File dir = new File(dirPath) ;
        String[] csvFiles = dir.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String fileName) {
                return fileName.endsWith(CSV_EXTENSION);
            }
        }) ;

        return csvFiles.length > 0 ;
    }

    private static boolean isCSVFile(String fileName) {
        return fileName.endsWith(CSV_EXTENSION) ;
    }

    private static String[] getCSVFileNames(String pathToCSVFiles) {
        File dir = new File(pathToCSVFiles) ;
        return dir.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String fileName) {
                return fileName.endsWith(CSV_EXTENSION);
            }
        }) ;
    }

    private static void processCSV(final String csvFilePath,
                                   final TableConfig csvFileConfig) {

         Thread readCSVThread = new Thread(new Runnable() {
             @Override
             public void run() {
                CSVParser csvParser = new CSVParser() ;
                String currCSVContent = csvParser.read(csvFilePath) ;
                writeSQLDumpFile(currCSVContent, csvFileConfig) ;
             }
         });

        readCSVThread.start();
    }

    private static ArrayList<TableConfig> loadTableConfigs(String pathToTableConfigs) {
        ArrayList<TableConfig> tableConfigs = new ArrayList<TableConfig>() ;

        if(tableConfigFileExists(pathToTableConfigs)) {
            tableConfigs = readConfigs(pathToTableConfigs) ;
        }

        return tableConfigs ;
    }

    private static boolean tableConfigFileExists(String pathToConfigFile) {
        return new File(pathToConfigFile).exists() ;
    }

    private static ArrayList<TableConfig> readConfigs(String pathToConfigFile) {
        ArrayList<TableConfig> tableConfigs = new ArrayList<TableConfig>() ;

        try {
            FileUtils configFileUtils = new FileUtils(pathToConfigFile) ;
            configFileParser = new ConfigFileParser(configFileUtils.read()) ;
            tableConfigs = configFileParser.parseConfigs() ;
        } catch (IOException e) {
            screen.displayText(e.getMessage());
            System.exit(0);
        }
        return tableConfigs ;
    }

    private static void writeSQLDumpFile(String csvContent, TableConfig csvFileConfig) {

    }

    static class GetParameters {

        static String PATH_FLAG = "-p" ;
        static String SQL_DUMP_OUTPUT_PATH_FLAG = "-o" ;
        static String DEFAULTS_FLAG = "-d" ;
        static String DROP_TABLE_FLAG = "-a" ;

        static String getPath(HashMap<String, String> params) {
            return params.get(PATH_FLAG) ;
        }

        static String getSQLDumpFilePathOutput(HashMap<String, String> params) {
            return params.get(SQL_DUMP_OUTPUT_PATH_FLAG) ;
        }

        static boolean getDefaults(HashMap<String, String> params) {
            return (params.get(DEFAULTS_FLAG) == "true") ? true : false ;
        }

        static boolean getDropTable(HashMap<String, String> params) {
            return (params.get(DROP_TABLE_FLAG) == "true") ? true : false ;
        }

    }
}
