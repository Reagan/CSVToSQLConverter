package org.ampathkenya.utils;

import junit.framework.TestCase;

import java.io.IOException;

public class FileUtilsTest extends TestCase {


    private final String TEST_FILE_NAME = "test_file.txt" ;
    private FileUtils fileUtils ;

    public void setUp() throws Exception {
        fileUtils = new FileUtils(TEST_FILE_NAME) ;
    }

    public void tearDown() throws Exception {
        fileUtils = null ;
    }

    public void testThatFileUtilWritesAndReadsFile() throws IOException {
        final String TEST_STRING = "this is a test" ;
        fileUtils.write(TEST_STRING) ;

        assertEquals("Test that string contents read are the same", TEST_STRING,
                fileUtils.read());
    }

}
