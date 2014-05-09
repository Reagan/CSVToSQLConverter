package org.ampathkenya.utils;

import junit.framework.TestCase;

import java.util.HashMap;

public class ArgumentProcessorTest extends TestCase {

    private ArgumentProcessor argumentProcessor ;

    @Override
    public void setUp() {
        argumentProcessor = new ArgumentProcessor() ;
    }

    @Override
    public void tearDown() throws Exception {
        argumentProcessor = null ;
    }

    public void testThatArgumentProcessorExtractsArgsAndValues() {

        String commandString = "command -a firstArg -b secondArg -c thirdArg" ;
        String command = "command" ;

        HashMap<String, String> commandArgs ;

        commandArgs = argumentProcessor.extractArgsAndProperties(command, commandString) ;

        assertNotNull("Test that extracted args and params are not null", commandArgs);
        assertTrue("Test that the correct number of args & params is returned",argumentProcessor.noOfArgs() == 3);
        assertEquals("Test first argument", "firstArg", commandArgs.get("a"));
        assertEquals("Test second argument", "secondArg", commandArgs.get("b")); ;
        assertEquals("Test third argument", "thirdArg", commandArgs.get("c")); ;
    }

    public void testThatArgumentProcessorReplacesMostRecentParamToArg() {
        String command = "command -m firstArg -v secondArg -m thirdArg" ;

        // test number of params

        // test args and params


    }

    /**
     * //@throws FlagException
     */
    public void testThatArgumentProcessorFlagsUnspecifiedArgFlag() {
        String command = "command -m firstArg - v secondArg -c thirdArg" ;

    }

    /**
     * //@throws UnspecifiesParamException
     */
    public void testThatArgumentProcessorFlagsUnspecifiedParam() {
        String command = "command -m firstArg secondArg -c thirdArg" ;
    }

    /**
     * //@throws UnSpecifiedFlagException
     */
    public void testThatArgumentProcessorThrowsErrorForUndefinedFlags() {
        String command = "command -m firstArg -b -c thirdArg" ;

    }

    /**
     * //@throws UnspecifiedCommandException
     */
    public void testThatArgumentProcessorThrowsErrorForMissingCommand() {

        String command = " -a firstArg -b secondArg" ;

    }

    /**
     * //@throws UnsupportedFlagException
     */
    public void testThatArgumentProcessorThrowsErrorForUnsupportedFlags() {
        String command = "command -a firstArg -b secondArg -x unsupportedArg" ;

    }
}
