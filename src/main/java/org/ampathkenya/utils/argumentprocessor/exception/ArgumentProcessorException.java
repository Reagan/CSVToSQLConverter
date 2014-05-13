package org.ampathkenya.utils.argumentprocessor.exception;

public abstract class ArgumentProcessorException extends Exception {

    public ArgumentProcessorException() {
        super();
    }

    public ArgumentProcessorException(String errorMessage) {
        super(errorMessage);
    }
}
