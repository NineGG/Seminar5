/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package se.kth.iv1350.seminar5.view;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import se.kth.iv1350.seminar5.controller.ActionFailedException;
import se.kth.iv1350.seminar5.controller.Controller;
import se.kth.iv1350.seminar5.integration.ItemInventoryResultLessThanZeroException;
import se.kth.iv1350.seminar5.model.dto.ReceiptDTO;
import se.kth.iv1350.seminar5.integration.dto.ItemDTO;
import se.kth.iv1350.seminar5.model.InsufficientPaymentException;
import se.kth.iv1350.seminar5.model.ItemAmountOverInventoryLimitException;
import se.kth.iv1350.seminar5.model.Observer;
import se.kth.iv1350.seminar5.model.dto.SaleStateDTO;

/**
 * A simple View Class.
 * @author nilse
 */
public class View{
    private final Controller contr;
    
    private ReceiptDTO receipt;
    
    private SaleStateDTO saleState;
    
    private final TotalRevenueFileOutput revenueFileOutput;
    private final TotalRevenueView revenueView;
    
    
    /**
     * Creates a View object.
     * @param contr A controller.
     * @throws IOException If LogWriter is unable to start.
     */
    public View(Controller contr) throws IOException{
        this.contr = contr;
        
        revenueFileOutput = new TotalRevenueFileOutput();
        revenueView = new TotalRevenueView();
    }
    
    private void saleStatePrinter(SaleStateDTO saleState) {
        
        if (saleState.getItemDTO().getItemAmount() > 1){
            System.out.println("Added " + saleState.getItemAmountChange() + 
                    " " + saleState.getItemDTO().getItemName() + " items to sale" );
        } else {
            System.out.println("Added " + saleState.getItemAmountChange() + 
                        " " + saleState.getItemDTO().getItemName() + " item to sale" );
        }
        
        System.out.println("Current running total: " + saleState.getRunningTotal() + " SEK");

        System.out.println();
    }
    
    private void receiptPrinter(ReceiptDTO receipt) {
        String dateAndTimeString;
        LocalDateTime dateAndTime;
        
        dateAndTime = receipt.getDateAndTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
        dateAndTimeString = dateAndTime.format(formatter);
        

        System.out.println("\n------------------ Begin receipt -------------------\n"
            + "Time of Sale: " + dateAndTimeString);
        System.out.println();

        for (ItemDTO item : receipt.getItemList()){
            System.out.println(item.getItemName() + "\t " + item.getItemAmount() + " x " + item.getPrice()
                    + " SEK");
        }

        System.out.println("\nTotal: \t" + receipt.getCostAfterDiscount() + " SEK\nVAT: \t" + receipt.getTotalVAT() + " SEK" + 
                "\n------------------ End receipt -------------------\n\n" +
                "Change to give to the customer: " + receipt.getChange() + " SEK");
        
    }
    
    
    /**
     * Fake customer interaction.
     */
    public void fakeCustomer(){
        
        startSale();
        
        addItem(423);
        addItem(231,3);
        addItem(123,3);
        addItem(111);
        addItem(423, 101);
        
        endSale();
        
        pay(500);
        
        System.out.println("End of fakeCustomer interaction");
        System.out.println();
        System.out.println();
    }
    
    /**
     * Another fake customer interaction.
     */
    public void fakeCustomerTwo(){
        
        startSale();
        
        addItem(423);
        addItem(231,3);
        addItem(123,3);
        addItem(111);
        addItem(423, 101);
        
        endSale();
        
        pay(20);
        pay(500);
        
        System.out.println("End of fakeCustomerTwo interaction");
        System.out.println();
        System.out.println();
    }
    
    private void addItem(int itemId) {
        try {
            saleState = contr.addItem(itemId);
            saleStatePrinter(saleState);
        } catch (ActionFailedException e) {
            System.out.println("Action could not be performed");
            System.out.println();
        } catch (ItemAmountOverInventoryLimitException e) {
            System.out.println("Attempted to add " + e.getItemDTO().getItemAmount() + 
                    " of item " + e.getItemDTO().getItemName() + " to the transaction, which is " + e.getOverTheMax() + 
                    " over what the inventory system has registered, perhaps you scanned something twice?");
            System.out.println();
        }
    }
    
    private void addItem(int itemId, int itemAmount) {
        try {
            saleState = contr.addItem(itemId, itemAmount);
            saleStatePrinter(saleState);
        } catch (ActionFailedException e) {
            System.out.println("Action could not be performed");
            System.out.println();
        } catch (ItemAmountOverInventoryLimitException e) {
            System.out.println("Attempted to add " + e.getItemDTO().getItemAmount() + 
                    " of item " + e.getItemDTO().getItemName() + " to the transaction, which is " + e.getOverTheMax() + 
                    " over what the inventory system has registered, perhaps you scanned something twice?");
            System.out.println();
        }
    }
    
    private void pay(int payment) {
        try {
            receipt = contr.payment(payment);
            receiptPrinter(receipt);
            System.out.println();
            
        } catch (ItemInventoryResultLessThanZeroException e) {
            System.out.println("Inventory system does not have " + e.getItemDTO().getItemAmount() + ", it only records" 
            + e.getInventoryItemDTO().getItemAmount() + ", perhaps you scanned too many items? If not plase contact a developer.");
            System.out.println();
        } catch (InsufficientPaymentException e) {
            System.out.println("Insufficient funds, attempted to pay " + e.getPayment() + " SEK, requires " + e.getCost() + " SEK");
            System.out.println();
        } catch (ActionFailedException e) {
            System.out.println("Action could not be performed");
            System.out.println();
        }
    }
    
    private void startSale() {
        contr.startSale();
        contr.addObserver(revenueView);
        contr.addObserver(revenueFileOutput);
        
        System.out.println("Starting new sale");
        System.out.println();
    }
    
    private void endSale() {
        double endOfSaleCost = contr.endSale();
        System.out.println("Ending sale, to complete sale please pay " + endOfSaleCost + " SEK");
        System.out.println();
    }
    
}
