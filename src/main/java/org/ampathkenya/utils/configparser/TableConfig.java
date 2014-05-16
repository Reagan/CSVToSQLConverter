package org.ampathkenya.utils.configparser;

import org.ampathkenya.utils.sqlgenerator.OrderedProperties;

public class TableConfig {

    private String tableName ;
    private OrderedProperties properties ;

    public TableConfig(String tableName, OrderedProperties properties) {
        this.tableName = tableName ;
        this.properties = properties ;
    }

    public String getTableName() {
        return tableName;
    }

    public OrderedProperties getTableConfigProperties() {
        return properties;
    }

    public String getProperty(String property) {
        String extractedProperty = "" ;
        if(properties.containsKey(property)) {
            extractedProperty = properties.getProperty(property) ;
        }
        return extractedProperty ;
    }

}
