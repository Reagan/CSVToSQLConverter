package org.ampathkenya.utils.configparser;


import java.io.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConfigFileParser {

    private final String OPENING_SQUARE_BRACKET = "[";
    private final String CLOSING_SQUARE_BRACKET = "]";
    private ArrayList<TableConfig> tableConfigs = new ArrayList<TableConfig>();

    public ConfigFileParser(String configs) throws IOException {
        if (configs.length() > 0) {
            tableConfigs = parseConfigs(configs);
        }
    }

    public ArrayList<TableConfig> parseConfigs(String configs) throws IOException {
        return createTableConfigs(extractTables(configs));
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
            Properties currTableProperties = extractTableProperties(currentTableConfig);

            TableConfig currTableConfig = new TableConfig(currTableName, currTableProperties);
            extractedTableConfigs.add(currTableConfig);
        }

        return extractedTableConfigs;
    }

    private String extractTableName(String tableConfig) {
        return tableConfig.substring(1, tableConfig.indexOf(CLOSING_SQUARE_BRACKET)) ;
    }

    private Properties extractTableProperties(String tableConfig) throws IOException {

        Properties extractedProperties = new Properties();
        tableConfig = tableConfig.substring(tableConfig.indexOf(CLOSING_SQUARE_BRACKET));
        extractedProperties.load(new ByteArrayInputStream(tableConfig.getBytes()));
        return extractedProperties;
    }
}
