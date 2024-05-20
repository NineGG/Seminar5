/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package se.kth.iv1350.seminar5.model;
import java.util.ArrayList;
import java.util.List;
import se.kth.iv1350.seminar5.integration.dto.*;
import se.kth.iv1350.seminar5.model.dto.*;
import se.kth.iv1350.seminar5.integration.ExternalInventorySystemAccessPoint;

/**
 * The class used to manage a new Sale.
 * @author nilse
 */
public class Sale {
    private final Receipt receipt;
    private final List<Observer> observerList;

    /**
     * Creates a Sale instance and a Receipt instance to go with it.
     */
    public Sale(){        
        receipt = new Receipt();
        ExternalInventorySystemAccessPoint.getInstance();
        observerList = new ArrayList<>();
    }
    /**
     * Adds an item to the receipt, 
     * if the item already is on the receipt, increase that items amount instead.
     * 
     * @param itemDTO The item to add.
     * @return A DTO containing information about the sale and the item.
     * @throws ItemAmountOverInventoryLimitException If item amount attempts to go above allowed Inventory limit.
     */
    public SaleStateDTO addItemToReceipt(ItemDTO itemDTO) throws ItemAmountOverInventoryLimitException{
        if (receipt.itemExists(itemDTO)){
            return receipt.increaseItemAmount(itemDTO);
        } else{
            return receipt.addItemToReceipt(itemDTO);
        }
    }
    
    /**
     * Adds a new observer to the sale.
     * @param obs The observer
     */
    public void addObserver(Observer obs) {
        observerList.add(obs);
    }
    
    /**
     * Gets the items stored in the current sale.
     * 
     * @return A list of ItemDTO objects from the current sale.
     */
    public List<ItemDTO> getItemList(){
        return receipt.getItemList();
    }
    
    
    /**
     * Gets the running total of the current sale.
     * 
     * @return An int representing the running total of the current Sale.
     */
    public double getRunningTotal(){
        return receipt.getCurrentCost();
    }
    
    
    /**
     * Payment of the sale.
     * 
     * @param payment The paid amount in SEK.
     * @return A string representing the receipt and the change.
     * @throws InsufficientPaymentException When payment is insufficient.
     */
    public ReceiptDTO payment(int payment) throws InsufficientPaymentException{
        receipt.payment(payment);
        ReceiptDTO receiptDTO = receipt.getReceiptDTO();
        updateObservers(receiptDTO);
        return receiptDTO;
    }
    
    private void updateObservers(ReceiptDTO receiptDTO) {
        for (Observer obs : observerList) {
            obs.updateTotalRevenue(receiptDTO.getCostAfterDiscount());
        }
    }
    
    /**
     * Get sale ingo.
     * @return An ReceiptDTO object that contains all relevant information about the sale.
     */
    public ReceiptDTO getSaleInfo(){
        return new ReceiptDTO(receipt);
    }
    
    
    
    
    
    
}
