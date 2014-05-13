package org.ampathkenya.utils.argumentprocessor.exception;

public class UndefinedValueException extends Exception {

    private String errorMessage ;

    public UndefinedValueException(String errorMessage) {
        super(errorMessage);
    }
}
