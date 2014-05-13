package org.ampathkenya.utils.configparser;

import java.util.Properties;

public class TableConfig {

    private String tableName ;
    private Properties properties ;

    public TableConfig(String tableName, Properties properties) {
        this.tableName = tableName ;
        this.properties = properties ;
    }

    public String getTableName() {
        return tableName;
    }

    public String getProperty(String property) {
        String extractedProperty = "" ;
        if(properties.containsKey(property)) {
            extractedProperty = properties.getProperty(property) ;
        }
        return extractedProperty ;
    }

}
