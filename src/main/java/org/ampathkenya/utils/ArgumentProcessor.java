package org.ampathkenya.utils;

import java.util.HashMap;
import java.util.Vector;

public class ArgumentProcessor {

    private static String ARG_MARKER = "-" ;
    public static String ARG_PARAM_SEPARATOR = "\\s" ;

    public static HashMap<String, String> extractArgsAndValues(String argsAndValues) {
        HashMap<String, String> extractedArgsAndValues = new HashMap<String, String>() ;

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

    private static Vector<String> extractTokensWithArgsAndValues(String argsAndValues) {
        Vector argsAndValuesTokens = new Vector() ;

        while (argsAndValues.length() > 0) {
            int indexOfArgMarker = getIndexOfArgMarker(argsAndValues) ;
            int indexOfNextArgMarker = getIndexOfArgMarker(argsAndValues.substring(1)) ;

            String argAndMarker = argsAndValues.substring(indexOfArgMarker,
                    indexOfNextArgMarker) ;

            argsAndValuesTokens.add(argAndMarker);

            argsAndValues = argsAndValues.substring(indexOfNextArgMarker);
        }

        return argsAndValuesTokens ;
    }

    private static Property splitStringWithArgAndValue(String argAndValue) {
        String arg = argAndValue.substring(argAndValue.indexOf(ARG_MARKER),
                argAndValue.indexOf(ARG_PARAM_SEPARATOR));

        String param = argAndValue.substring(argAndValue.indexOf(ARG_PARAM_SEPARATOR),
                argAndValue.length());

        return new Property(arg, param) ;
    }

    private static int getIndexOfArgMarker(String stringWithArgMarker) {

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
