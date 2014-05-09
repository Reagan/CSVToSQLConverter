package org.ampathkenya.utils;

import java.util.HashMap;
import java.util.Vector;

public class ArgumentProcessor {

    public char ARG_PARAM_SEPARATOR = ' ' ;

    private String ARG_MARKER = "-" ;
    private HashMap<String, String> extractedArgsAndValues = new HashMap<String, String>() ;

    public ArgumentProcessor() {}

    public HashMap<String, String> extractArgsAndProperties (String mainCommand,
                                                             String commandsAndParameters)  {
        HashMap<String, String> commandsAndProperties
                = new HashMap<String, String>() ;

        if(checkMainCommand(mainCommand, commandsAndParameters)) {
            commandsAndProperties
                    = extractCommandParameters(extractCommandFromString(mainCommand,
                                                                        commandsAndParameters));
        } else {
            // throw exception
        }

        return commandsAndProperties ;
    }

    private static boolean checkMainCommand(String mainCommand, String commandsAndParameters) {
        if(commandsAndParameters.startsWith(mainCommand)) {
            return true ;
        }
        return false ;
    }

    private static String extractCommandFromString(String commandToExtract,
                                                     String commandAndParameters) {
        return commandAndParameters
                .substring(commandAndParameters.indexOf(commandToExtract) + commandToExtract.length(),
                        commandAndParameters.length());
    }

    private HashMap<String, String> extractCommandParameters(String commandParameters) {
        return extractArgsAndValues(commandParameters.trim()) ;
    }

    private HashMap<String, String> extractArgsAndValues(String argsAndValues) {

        Vector<String> setOfArgsAndValues = extractTokensWithArgsAndValues(argsAndValues) ;

        for (int argsAndValuesCounter = 0  ;
                argsAndValuesCounter < setOfArgsAndValues.size() ;
                argsAndValuesCounter++) {

            String currArgAndValue = setOfArgsAndValues.elementAt(argsAndValuesCounter) ;
            Property extractedArgAndValue = splitStringWithArgAndValue(currArgAndValue);

            extractedArgsAndValues.put(extractedArgAndValue.param,
                    extractedArgAndValue.value) ;
        }
        return extractedArgsAndValues ;
    }

    public int noOfArgs() {
        return extractedArgsAndValues.size() ;
    }

    private Vector<String> extractTokensWithArgsAndValues(String argsAndValues) {
        Vector argsAndValuesTokens = new Vector() ;
        final int OFFSET = 1 ;

        while (argsAndValues.length() > 0) {
            int indexOfArgMarker = getIndexOfArgMarker(argsAndValues) ;
            int indexOfNextArgMarker = getIndexOfArgMarker(argsAndValues.substring(1)) ;

            String argAndMarker = "" ;

            if((indexOfNextArgMarker - indexOfArgMarker + 1)
                    == argsAndValues.length()) {
                argAndMarker = argsAndValues.substring(indexOfArgMarker,
                        (indexOfNextArgMarker +1)) ;
            } else {
                argAndMarker = argsAndValues.substring(indexOfArgMarker,
                    indexOfNextArgMarker) ;
            }

            argsAndValuesTokens.add(argAndMarker);

            argsAndValues = argsAndValues.substring(indexOfNextArgMarker + OFFSET);
        }

        return argsAndValuesTokens ;
    }

    private Property splitStringWithArgAndValue(String argAndValue) {
        final int OFFSET = 1 ;

        String arg = argAndValue.substring(argAndValue.indexOf(ARG_MARKER) + OFFSET,
                argAndValue.indexOf(ARG_PARAM_SEPARATOR));

        String param = argAndValue.substring(argAndValue.indexOf(ARG_PARAM_SEPARATOR) + OFFSET,
                argAndValue.length());

        return new Property(arg, param) ;
    }

    private int getIndexOfArgMarker(String stringWithArgMarker) {

        int indexOfArgMarker = stringWithArgMarker.length() ;

        if(stringWithArgMarker.contains(ARG_MARKER)) {
            indexOfArgMarker = stringWithArgMarker.indexOf(ARG_MARKER);
        }

        return indexOfArgMarker ;
    }

    static class Property {
        String param ;
        String value ;

        Property(String param, String value) {
            this.param = param ;
            this.value = value ;
        }
    }
}
