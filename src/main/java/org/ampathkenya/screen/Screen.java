package org.ampathkenya.screen;

import org.ampathkenya.screen.config.ScreenConfig;
import org.ampathkenya.utils.IOUtils;
import org.ampathkenya.utils.StringUtils;

import java.io.IOException;
import java.util.HashMap;

public class Screen {

    protected static Screen screen ;
    protected ScreenConfig config;

    private Screen() {}

    public static Screen initialize() {
        return screen = new Screen() ;
    }

    public <T> void displayText(T messageToDisplay) {
        outputToScreen(messageToDisplay.toString()) ;
    }

    public String receiveInput(String prompt) {
        outputToScreen(prompt);
        return IOUtils.read(System.in) ;
    }

    public HashMap<String, String> getCommandParameters(String commandAndParameters,
                                                            String mainCommand) {
        return StringUtils.extractArgsAndProperties(commandAndParameters, mainCommand) ;
    }

    private void outputToScreen(String stringToOutput) {
        System.out.println(stringToOutput);
    }

}
