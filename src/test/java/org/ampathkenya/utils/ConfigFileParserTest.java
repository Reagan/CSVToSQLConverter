package org.ampathkenya.utils;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.ampathkenya.utils.configparser.ConfigFileParser;
import org.ampathkenya.utils.configparser.TableConfig;

import java.io.IOException;
import java.util.ArrayList;

public class ConfigFileParserTest extends TestCase {

    private ConfigFileParser configFileParser ;
    private String configs;
    private ArrayList<TableConfig> tableConfigs;
    protected void setUp() {
        configs = "[table1]\n" +
                 "id=int\n" +
                 "firstName=varchar(255)\n" +
                 "secondName=varchar(255)\n" +
                 "gender=enum('M','F')\n" +
                 "[table2]\n" +
                 "id=int\n" +
                 "firstName=varchar(255)\n" +
                 "secondName=varchar(255)\n" +
                 "gender=enum('M','F')\n";

        try {
            configFileParser = new ConfigFileParser(configs) ;
            tableConfigs = configFileParser.parseConfigs(configs);
        } catch (IOException e) {}
    }

    protected void tearDown() {
        configFileParser = null ;
    }

    public void testThatConfigParserObjectIsCreated() throws IOException{
        Assert.assertNotNull("Test that table configs not null",tableConfigs);
        Assert.assertEquals("Test that size of tableConfig is 2", 2, tableConfigs.size());
    }

    public void testThatTableConfigReturnsCorrectTableName() throws IOException{
        Assert.assertEquals("Test that tableConfig returns table1", "table1", tableConfigs.get(0).getTableName());
    }

    public void testThatTableConfigReturnsCorrectTableConfigProperties() throws IOException{
        Assert.assertEquals("Test that tableConfig for id returns int","int",tableConfigs.get(0).getProperty("id"));
        Assert.assertEquals("Test that tableConfig for firstName returns varchar(255)","varchar(255)",tableConfigs.get(0).getProperty("firstName"));
        Assert.assertEquals("Test that tableConfig for secondName returns varchar(255)","varchar(255)",tableConfigs.get(0).getProperty("secondName"));
        Assert.assertEquals("Test that tableConfig for gender returns enum('M','F')","enum('M','F')",tableConfigs.get(0).getProperty("gender"));
    }
}
