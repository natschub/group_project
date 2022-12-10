package edu.upenn.cit594.logging;

import java.io.FileWriter;
import java.io.IOException;

public class Logger {
    private Logger() throws IOException {}
    private FileWriter out;
    private String filename;


    public void setLoggerOutputFile(String name) throws IOException {
        filename = name;
        if (out != null) {
            out.close();
        }
        out = new FileWriter(filename, true);
    }
    private static Logger instance;

    static {
        try {
            instance = new Logger();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /// accessor method
    public static Logger getInstance() {return instance;}

    //non-static method
    public void log(String msg) throws IOException {
        out.write(msg + "\n");
        out.flush();
    }
}

