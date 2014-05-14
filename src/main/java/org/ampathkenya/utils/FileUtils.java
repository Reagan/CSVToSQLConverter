package org.ampathkenya.utils;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtils {

    private FileReader fileReader = null ;
    private FileWriter fileWriter = null ;
    private String filePath =  "" ;
    private File file = null ;

    public FileUtils(String filePath) {
        this.filePath = filePath ;
        file = new File(filePath) ;
    }

    public String read() throws IOException {
        fileReader = new FileReader(file);
        char[] fileContents = new char[(int) file.length()] ;
        fileReader.read(fileContents) ;
        fileReader = null ;
        return new String(fileContents) ;
    }

    public void write(String content) throws IOException {
        fileWriter = new FileWriter(new File(filePath), false) ;
        fileWriter.write(content) ;
        fileWriter.close();
        fileWriter = null ;
    }
}

