/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package se.kth.iv1350.seminar5.controller;
import java.io.IOException;
import se.kth.iv1350.seminar5.model.Sale;
import java.util.List;
import se.kth.iv1350.seminar5.integration.DatabaseUnresponsiveException;
import se.kth.iv1350.seminar5.integration.ExternalAccountingSystemAccessPoint;
import se.kth.iv1350.seminar5.integration.ExternalInventorySystemAccessPoint;
import se.kth.iv1350.seminar5.integration.ItemInventoryResultLessThanZeroException;
import se.kth.iv1350.seminar5.integration.NoMatchingItemByIdException;
import se.kth.iv1350.seminar5.integration.dto.*;
import se.kth.iv1350.seminar5.model.InsufficientPaymentException;
import se.kth.iv1350.seminar5.model.ItemAmountOverInventoryLimitException;
import se.kth.iv1350.seminar5.model.Observer;
import se.kth.iv1350.seminar5.model.dto.*;
import se.kth.iv1350.seminar5.utilities.LogWriter;
;

/**
 * A controller class.
 * @author nilse
 */
public class Controller {
    private Sale sale;
    private final ExternalInventorySystemAccessPoint externInv;
    
    private final ExternalAccountingSystemAccessPoint accounting;
    
    private final LogWriter logger;
    
    /**
     * Adds an observer to the sale.
     * @param obs The observer.
     */
    public void addObserver(Observer obs) {
        sale.addObserver(obs);
    }
    
    /**
     * Creates a new Controller instance
     * @throws IOException if LogWriter is unable to start.
     */
    public Controller() throws IOException {
        this.externInv = ExternalInventorySystemAccessPoint.getInstance();
        this.accounting = ExternalAccountingSystemAccessPoint.getInstance();
        this.logger = new LogWriter();
    }
    
    /**
     * Starts a new sale.
     */
    public void startSale(){
        this.sale = new Sale();
    }
    
    /**
     * Ends the sale.
     * 
     * @return gets running total.
     */
    public double endSale(){
        return sale.getRunningTotal();
    }
    
    
    /**
     * A simple payment processing method.
     * 
     * @param payment The amount paid in SEK.
     * 
     * @return A string representing the receipt and the change
     * @throws ActionFailedException When database update would result in negative inventory.
     * @throws InsufficientPaymentException When payment is insufficient.
     */
    public ReceiptDTO payment(int payment) throws ActionFailedException, InsufficientPaymentException{
        
        ReceiptDTO printReceipt;
        try {
            printReceipt = sale.payment(payment);
            ItemListDTO itemListDTO = sale.getItemList();
            ReceiptDTO saleInfo = sale.getSaleInfo();
            accounting.updateAccounting(saleInfo);
            externInv.updateInventory(itemListDTO);
        } catch (ItemInventoryResultLessThanZeroException e) {
            logger.log(e);
            throw new ActionFailedException("Database call would result in negative inventory", e);
        }
        return printReceipt;
    }
    
    /**
     * Adds an item to the sale.
     * 
     * @param itemId The id of the item.
     * 
     * @return A SaleStateDTO containing information about the item and current total of the sale.
     * @throws ActionFailedException When no item with the provided id is found or could not connect to database.
     * @throws ItemAmountOverInventoryLimitException If item amount attempts to go above allowed Inventory limit.
     */
    public SaleStateDTO addItem(int itemId) throws ActionFailedException, ItemAmountOverInventoryLimitException {
        ItemDTO itemDTO;
        
        try {
            itemDTO = externInv.retrieveItem(itemId, 1);
            return sale.addItemToReceipt(itemDTO);
            
        } catch (NoMatchingItemByIdException e) {
            logger.log(e);
            throw new ActionFailedException("Could not find item", e);
        } catch (DatabaseUnresponsiveException e) {
            logger.log(e);
            throw new ActionFailedException("Could not connect to database", e);
        }
        
    }
    
    /**
     * Adds items to the sale.
     * 
     * @param itemId The id of the item.
     * @param amount The amount of said item.
     * 
     * @return A SaleStateDTO containing information about the item and current total of the sale.
     * @throws ActionFailedException When no item with the provided id is found or could not connect to database.
     * @throws ItemAmountOverInventoryLimitException If item amount attempts to go above allowed Inventory limit.
     */
    public SaleStateDTO addItem(int itemId, int amount) throws ActionFailedException, ItemAmountOverInventoryLimitException {
        ItemDTO itemDTO;
        
        try {
            itemDTO = externInv.retrieveItem(itemId, amount);
            return sale.addItemToReceipt(itemDTO);
            
        } catch (NoMatchingItemByIdException e) {
            logger.log(e);
            throw new ActionFailedException("Could not find item", e);
        } catch (DatabaseUnresponsiveException e) {
            logger.log(e);
            throw new ActionFailedException("Could not connect to database", e);
        } 
    }
}
