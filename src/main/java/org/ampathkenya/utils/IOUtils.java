package org.ampathkenya.utils;

import java.io.*;

public class IOUtils {

    private final String RETRY_ERROR_MESSAGE = "There was an error receiving your input. Please try again" ;

    public static String read(InputStream inputStream) {
        String readString = "" ;

        try {
            BufferedReader bufferedReader
                    = new BufferedReader(new InputStreamReader(inputStream)) ;
            readString = bufferedReader.readLine() ;
        } catch (IOException e) {
            System.out.println(e.toString());
        }

        return readString ;
    }
}
