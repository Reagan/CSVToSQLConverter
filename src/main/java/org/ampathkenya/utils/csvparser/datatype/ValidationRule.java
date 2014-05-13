package org.ampathkenya.utils.csvparser.datatype;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class ValidationRule {

    private String validationRule ;
    private Pattern pattern ;

    public ValidationRule(String validationRule) {
        if(checkThatRuleIsValid(validationRule)) {
            this.validationRule = validationRule ;
        }
    }

    public String getValidationRule() {
        return validationRule ;
    }

    public boolean validate(String stringToBeValidated) {
        boolean matchExists = false ;
        Matcher matcher = pattern.matcher(stringToBeValidated) ;

        while(matcher.matches()) {
            matchExists = true ;
            break;
        }

        return matchExists ;
    }

    private boolean checkThatRuleIsValid(String regexValidationRule) {

        try {
            pattern =  Pattern.compile(regexValidationRule) ;
            return true ;
        } catch (PatternSyntaxException p) {}

        return false ;
    }
}
