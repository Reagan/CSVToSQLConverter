package org.ampathkenya.utils.configparser;


import org.ampathkenya.utils.sqlgenerator.OrderedProperties;

import java.io.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConfigFileParser {

    private final String OPENING_SQUARE_BRACKET = "[";
    private final String CLOSING_SQUARE_BRACKET = "]";
    private String configs ;
    private ArrayList<TableConfig> tableConfigs = new ArrayList<TableConfig>();

    public ConfigFileParser(String configs) throws IOException {
        if (configs.length() > 0) {
            this.configs = configs ;
        }
    }

    public ArrayList<TableConfig> parseConfigs() throws IOException {
        tableConfigs = createTableConfigs(extractTables(configs));
        return tableConfigs ;
    }

    private ArrayList<String> extractTables(String configs) {
        ArrayList<String> extractedTableData = new ArrayList<String>();

        while (configs.contains(OPENING_SQUARE_BRACKET)) {
            int firstSquareBracket = configs.indexOf(OPENING_SQUARE_BRACKET);
            int secondSquareBracket = (configs.substring(1)).indexOf(OPENING_SQUARE_BRACKET);

            if(secondSquareBracket == -1) {
                secondSquareBracket = configs.length() ;
            }

            String tableConfigData = configs.substring(firstSquareBracket,
                    secondSquareBracket);

            extractedTableData.add(tableConfigData);
            configs = configs.substring(secondSquareBracket).replaceFirst("\n", "");
        }
        return extractedTableData;
    }

    private ArrayList<TableConfig> createTableConfigs(ArrayList<String> tableConfigs) throws IOException {

        ArrayList<TableConfig> extractedTableConfigs = new ArrayList<TableConfig>();

        for (int tablesConfigCounter = 0;
             tablesConfigCounter < tableConfigs.size();
             tablesConfigCounter++) {

            String currentTableConfig = tableConfigs.get(tablesConfigCounter);

            String currTableName = extractTableName(currentTableConfig);
            OrderedProperties currTableProperties = extractTableProperties(currentTableConfig);

            TableConfig currTableConfig = new TableConfig(currTableName, currTableProperties);
            extractedTableConfigs.add(currTableConfig);
        }

        return extractedTableConfigs;
    }

    private String extractTableName(String tableConfig) {
        return tableConfig.substring(1, tableConfig.indexOf(CLOSING_SQUARE_BRACKET)) ;
    }

    private OrderedProperties extractTableProperties(String tableConfig) throws IOException {

        OrderedProperties extractedProperties = new OrderedProperties();
        tableConfig = tableConfig.substring(tableConfig.indexOf(CLOSING_SQUARE_BRACKET));
        extractedProperties.load(new ByteArrayInputStream(tableConfig.getBytes()));
        return extractedProperties;
    }
}
