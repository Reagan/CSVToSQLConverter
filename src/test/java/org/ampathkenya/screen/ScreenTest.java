package org.ampathkenya.screen;

import junit.framework.TestCase;

import org.ampathkenya.screen.Screen ;

public class ScreenTest extends TestCase {

    private Screen screen ;

    @Override
    public void setUp() {
        screen = new Screen()  ;
    }

    public void testThatScreenObjectWasCreated() {
        assertNotNull("Test that instance of screen created is not null", screen);
        assertTrue("Test that instance of screen has been created", screen instanceof Screen);
    }

    public void testThatScreenDisplaysText() {
        String displayedText = "This is displayed text" ;
        // assertSame(screen.outputToScreen(displayedText), displayedText,
        //        "Test that the string displayed is the same as required");
    }

    public void testThatScreenReceivesEnteredText() {

    }

    @Override
    public void tearDown() {
        screen = null ;
    }
}
