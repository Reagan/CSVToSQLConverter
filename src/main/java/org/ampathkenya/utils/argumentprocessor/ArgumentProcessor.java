package org.ampathkenya.utils.argumentprocessor;

import org.ampathkenya.screen.Screen;
import org.ampathkenya.utils.argumentprocessor.exception.UndefinedFlagException;
import org.ampathkenya.utils.argumentprocessor.exception.UndefinedValueException;
import org.ampathkenya.utils.argumentprocessor.exception.UnsupportedFlagException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

public class ArgumentProcessor {

    public char ARG_PARAM_SEPARATOR = ' ';

    private String ARG_MARKER = "-";
    private HashMap<String, String> extractedArgsAndValues = new HashMap<String, String>();
    private static ArrayList<String> supportedFlags = new ArrayList<String>() ;

    public ArgumentProcessor() {}

    public ArgumentProcessor(ArrayList<String> supportedFlags) {
        this.supportedFlags = supportedFlags ;
    }

    public HashMap<String, String> extractArgsAndProperties(String argsFlagsAndProperties) throws UndefinedValueException,
                                                                                                    UndefinedFlagException,
                                                                                                    UnsupportedFlagException  {
        HashMap<String, String> argFlagsAndProperties = new HashMap<String, String>();

        if (argsFlagsAndProperties.length() > 0) {
            argFlagsAndProperties = extractArgsAndValues(argsFlagsAndProperties.trim());
        }

        return argFlagsAndProperties;
    }


    private HashMap<String, String> extractArgsAndValues(String argsAndValues) throws UndefinedValueException,
                                                                                            UndefinedFlagException,
                                                                                            UnsupportedFlagException   {

        Vector<String> setOfArgsAndValues = extractTokensWithArgsAndValues(argsAndValues);

        for (int argsAndValuesCounter = 0;
             argsAndValuesCounter < setOfArgsAndValues.size();
             argsAndValuesCounter++) {

            String currArgAndValue = setOfArgsAndValues.elementAt(argsAndValuesCounter);
            Property extractedArgAndValue = splitStringWithArgAndValue(currArgAndValue);

            if (extractedArgAndValue.checkArgAndProperty()) {
                extractedArgsAndValues.put(extractedArgAndValue.param,
                        extractedArgAndValue.value);
            }
        }
        return extractedArgsAndValues;
    }

    public int noOfArgs() {
        return extractedArgsAndValues.size();
    }

    private Vector<String> extractTokensWithArgsAndValues(String argsAndValues) {
        Vector argsAndValuesTokens = new Vector();
        final int OFFSET = 1;

        while (argsAndValues.length() > 0) {
            int indexOfArgMarker = getIndexOfArgMarker(argsAndValues);
            int indexOfNextArgMarker = getIndexOfArgMarker(argsAndValues.substring(1));

            String argAndMarker = "";

            if ((indexOfNextArgMarker - indexOfArgMarker + 1)
                    == argsAndValues.length()) {
                argAndMarker = argsAndValues.substring(indexOfArgMarker,
                        (indexOfNextArgMarker + 1));
            } else {
                argAndMarker = argsAndValues.substring(indexOfArgMarker,
                        indexOfNextArgMarker);
            }

            argsAndValuesTokens.add(argAndMarker);

            argsAndValues = argsAndValues.substring(indexOfNextArgMarker + OFFSET);
        }

        return argsAndValuesTokens;
    }

    private Property splitStringWithArgAndValue(String argAndValue) {
        final int OFFSET = 1;

        argAndValue += ARG_PARAM_SEPARATOR;

        String arg = argAndValue.substring(argAndValue.indexOf(ARG_MARKER) + OFFSET,
                argAndValue.indexOf(ARG_PARAM_SEPARATOR));

        String param = argAndValue.substring(argAndValue.indexOf(ARG_PARAM_SEPARATOR) + OFFSET,
                argAndValue.length());

        return new Property(arg.trim(), param.trim());
    }

    private int getIndexOfArgMarker(String stringWithArgMarker) {

        int indexOfArgMarker = stringWithArgMarker.length();

        if (stringWithArgMarker.contains(ARG_MARKER)) {
            indexOfArgMarker = stringWithArgMarker.indexOf(ARG_MARKER);
        }

        return indexOfArgMarker;
    }



    static class Property {
        String param;
        String value;

        Property(String param, String value) {
            this.param = param;
            this.value = value;
        }

        boolean checkIfFlagIsSupported(String flag) {
            if(supportedFlags.size() > 0)
                return supportedFlags.contains(flag) ;

            return true ;
        }

        boolean checkArgAndProperty() throws UndefinedValueException,
                                             UndefinedFlagException,
                                             UnsupportedFlagException {

            if(checkIfFlagIsSupported(this.param)) {
                if (this.value == null || this.value.equals("")) {
                    throw new UndefinedValueException(
                            "Flag -"
                                    + this.param
                                    + " has no value");
                } else if (this.param == null || this.param == "") {

                    throw new UndefinedFlagException(
                            "Value "
                                    + this.param
                                    + " has no flag") ;

                }
            } else {
                throw new UnsupportedFlagException("Flag -"
                                + this.param
                                + " is not supported") ;
            }

            return true;

        }
    }
}
