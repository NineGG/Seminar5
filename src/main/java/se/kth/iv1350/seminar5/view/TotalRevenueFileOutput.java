/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package se.kth.iv1350.seminar5.view;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import se.kth.iv1350.seminar5.model.Observer;

/**
 * Prints total revenue from sales to an file.
 * @author Nils Ekenberg
 */
public class TotalRevenueFileOutput implements Observer{
    
    private double totalRevenueFromAllSales;
    private static final String LOG_FILE_NAME = "RevenueLog.txt";
    private final PrintWriter logFile;
    
    
    /**
     * Creates a new TotalRevenueFileOutput instance
     * @throws IOException If PrintWriter cannot be created.
     */
    public TotalRevenueFileOutput() throws IOException {
        totalRevenueFromAllSales = 0;
        logFile = new PrintWriter(new FileWriter(LOG_FILE_NAME, true));
        logFile.println();
        logFile.println("---------Program start: " + time() + "---------");
        logFile.flush();
    }
    
    private String time(){
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
        return dateTime.format(formatter);
    }

    
    /**
     * Prints to file based on the total revenue from sale and revenue from program start.
     * @param totalRevenueFromSale The total revenue from sale.
     */
    @Override
    public void updateTotalRevenue(double totalRevenueFromSale) {
        totalRevenueFromAllSales += totalRevenueFromSale;
        
        logFile.println("New sale completed. Revenue from sale: " + totalRevenueFromSale + " SEK");
        logFile.println("Total revenue since program start: " + totalRevenueFromAllSales + " SEK");
        logFile.println();
        logFile.flush();
        
    }
}
