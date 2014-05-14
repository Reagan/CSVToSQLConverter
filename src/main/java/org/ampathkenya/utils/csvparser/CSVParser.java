package org.ampathkenya.utils.csvparser;

import org.ampathkenya.utils.csvparser.datatype.DataType;
import org.ampathkenya.utils.csvparser.datatype.ValidationRule;

public class CSVParser {

    private final DataType VARCHAR = new DataType ("String", new ValidationRule("[a-zA-Z0-9]")) ;

    private DataType [] supportedDataTypes = {
            VARCHAR
    } ;

    public String read(String csvFilePath) {
        return "" ;
    }

}
