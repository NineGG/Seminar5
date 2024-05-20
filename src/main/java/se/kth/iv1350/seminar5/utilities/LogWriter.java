/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package se.kth.iv1350.seminar5.utilities;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * A LogWriter, Responsible to log relevant exceptions to the log file.
 * @author Nils Ekenberg
 */
public class LogWriter {
    private static final String LOG_FILE_NAME = "log.txt";
    private final PrintWriter logFile;
    
    
    /**
     * Creates a new LogWriter instance
     * @throws IOException If PrintWriter cannot be created.
     */
    public LogWriter() throws IOException {
        logFile = new PrintWriter(new FileWriter(LOG_FILE_NAME, true));
    }
    
    /**
     * Logs the exception to the log file.
     * @param exception The exception to log.
     */
    public void log(Exception exception){
        String message = exception.getMessage() + " Exception Thrown: " + time();
        logFile.println(message);
        exception.printStackTrace(logFile);
        logFile.flush();
    }
    
    private String time(){
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
        return dateTime.format(formatter);
    }
    
    
}
