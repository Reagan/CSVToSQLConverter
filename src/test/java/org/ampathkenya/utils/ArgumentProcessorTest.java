package org.ampathkenya.utils;

import junit.framework.TestCase;
import org.ampathkenya.utils.argumentprocessor.ArgumentProcessor;
import org.ampathkenya.utils.argumentprocessor.exception.UndefinedFlagException;
import org.ampathkenya.utils.argumentprocessor.exception.UndefinedValueException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.HashMap;

public class ArgumentProcessorTest extends TestCase {

    private ArgumentProcessor argumentProcessor ;

    @Rule
    public ExpectedException undefinedValueException = ExpectedException.none() ;

    @Override
    public void setUp() {
        argumentProcessor = new ArgumentProcessor() ;
    }

    @Override
    public void tearDown() throws Exception {
        argumentProcessor = null ;
    }

    @Test
    public void testThatArgumentProcessorExtractsArgsAndValues() {

        String commandString = "-a firstArg -b secondArg -c thirdArg" ;
        HashMap<String, String> commandArgs = new HashMap<String, String>() ;

        try {
           commandArgs = argumentProcessor.extractArgsAndProperties(commandString) ;
        } catch (Exception e) {}

        assertNotNull("Test that extracted args and params are not null", commandArgs);
        assertTrue("Test that the correct number of args & params is returned", argumentProcessor.noOfArgs() == 3);
        assertEquals("Test first argument", "firstArg", commandArgs.get("a"));
        assertEquals("Test second argument", "secondArg", commandArgs.get("b")); ;
        assertEquals("Test third argument", "thirdArg", commandArgs.get("c")); ;
    }

    @Test
    public void testThatArgumentProcessorReplacesMostRecentParamToArg() {
        String commandString = "-a firstArg -b secondArg -a thirdArg" ;
        HashMap<String, String> commandArgs = new HashMap<String, String>() ;

        try {
            commandArgs = argumentProcessor.extractArgsAndProperties(commandString) ;
        } catch (Exception e) {}

        assertTrue("Test that the correct number of args & params is returned",argumentProcessor.noOfArgs() == 2);
        assertEquals("Test first argument", "thirdArg", commandArgs.get("a"));
        assertEquals("Test first argument", "secondArg", commandArgs.get("b"));
    }

    @Test
    public void testThatArgumentProcessorFailsWhenFlagIsUnspecified() {
        String commandAndArgs = "-a" ;

        try {
            HashMap<String, String> commandArgs = argumentProcessor.extractArgsAndProperties(commandAndArgs) ;
        } catch (UndefinedValueException e) {
            assertEquals("Test that UndefinedValueException is thrown",
                    e.getMessage(), "Flag -a has no value");
        } catch (Exception e) {}
    }

    @Test
    public void testThatArgumentProcessorFailsWhenValueIsUnspecified() {
        String commandAndArgs = "-a firstArg secondArg -c thirdArg" ;

        try {
            HashMap<String, String> commandArgs = argumentProcessor.extractArgsAndProperties(commandAndArgs) ;
        } catch (UndefinedFlagException e) {
            assertEquals("Test that UndefinedFlagException is thrown",
                    e.getMessage(), "Value secondArg has no flag");
        } catch (Exception e) {}
    }

}
