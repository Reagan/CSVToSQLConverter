package org.ampathkenya.utils;

import java.util.HashMap;
import java.util.StringTokenizer;

public class StringUtils {

    public static HashMap<String, String>
            extractArgsAndProperties (String commandsAndParameters,
                                      String mainCommand)  {

        HashMap<String, String> commandsAndProperties
                 = new HashMap<String, String>() ;

        if(checkMainCommand(mainCommand, commandsAndParameters)) {
            commandsAndProperties
                    = extractCommandParameters(extractCommandFromString(mainCommand,
                        commandsAndParameters));
        }

        return commandsAndProperties ;
    }

    protected static boolean checkMainCommand(String mainCommand, String commandsAndParameters) {
        if(commandsAndParameters.startsWith(mainCommand)) {
            return true ;
        }
        return false ;
    }

    protected static String extractCommandFromString(String commandToExtract,
                                                     String commandAndParameters) {
        return commandAndParameters
                .substring(commandAndParameters.indexOf(commandToExtract),
                        commandAndParameters.length());
    }

    protected static HashMap<String, String> extractCommandParameters(String commandParameters) {
        return ArgumentProcessor.extractArgsAndValues(commandParameters) ;
    }
}
