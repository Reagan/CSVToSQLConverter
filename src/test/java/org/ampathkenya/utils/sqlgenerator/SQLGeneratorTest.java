package org.ampathkenya.utils.sqlgenerator;

import junit.framework.TestCase;
import org.ampathkenya.utils.configparser.TableConfig;

import java.util.Properties;

public class SQLGeneratorTest extends TestCase {

    private SQLGenerator sqlGenerator ;
    private String csvFileContent = "id,firstName,secondName\n" +
            "1,'First','User'\n" +
            "2,'Second','User'\n" +
            "3,'Third','User'\n" +
            "4,'Fourth','User'" ;

    private String tableName = "table1" ;
    private Properties properties = new Properties() ;

    private TableConfig tableConfig ;
    protected void setUp() {
        properties.setProperty("id", "int") ;
        properties.setProperty("firstName", "varchar(255") ;
        properties.setProperty("secondName", "varchar(255") ;

        tableConfig = new TableConfig(tableName, properties) ;
        sqlGenerator = new SQLGenerator(csvFileContent, tableConfig) ;
    }

    protected void tearDown() {

    }

    protected void testThatCSVConfigOptionsAreDisplayed() {
        String expectedDisplayedConfigs = "Converting 'table1' with configs\n" +
                "id=int\n" +
                "firstName=varchar(255)\n" +
                "secondName=varchar(255)\n" +
                "primarykey=id\n" +
                "autoincrement=true\n" +
                "foreignkey=id references shoes.owner_id" ;

        assertEquals("Test that correct set of configs are displayed",
                expectedDisplayedConfigs, sqlGenerator.displayTableConfig());


    }
}
