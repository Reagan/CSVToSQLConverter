package org.ampathkenya.utils.csvparser.datatype;

public class DataType {

    private String typeName ;
    private ValidationRule validationRule ;

    public DataType(String typeName, ValidationRule validationRule) {
        this.typeName = typeName ;
        this.validationRule = validationRule ;
    }
    public String getTypeName() {
        return typeName;
    }

    public ValidationRule getValidationRule() {
        return validationRule ;
    }

    /*
    public boolean validate(ValidationRule validateRule) {

    }
    */
}
