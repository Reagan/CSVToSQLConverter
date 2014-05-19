package org.ampathkenya;

import junit.framework.TestCase;
import org.ampathkenya.utils.FileUtils;

import java.io.IOException;

public class CSVToSQLConverterTest extends TestCase {

    private CSVToSQLConverter csvToSQLConverter ;
    private FileUtils fileUtils ;
    private final String CONFIG_FILE_PATH = "/home/developers/Desktop/test/Hholdmember.config"  ;

    public void setUp() throws IOException {
        csvToSQLConverter = new CSVToSQLConverter() ;
        fileUtils = new FileUtils(CONFIG_FILE_PATH) ;
        fileUtils.write("[table1]");
    }

    public void tearDown() {
       fileUtils.deleteFile() ;
    }

    public void testThatTableConfigPathIsReferenced() {
        try {
            assertTrue("Test that tableConfig is obtained",
                    CSVToSQLConverter.tableConfigFileExists(CONFIG_FILE_PATH));
        } catch (NullPointerException ne) {
            System.out.println(ne.getMessage());
        }
    }
}
