/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package se.kth.iv1350.seminar5.model.dto;
import java.time.LocalDateTime;
import se.kth.iv1350.seminar5.integration.dto.ItemDTO;
import java.util.List;
import se.kth.iv1350.seminar5.model.Receipt;

/**
 * A DTO class used to transfer Receipt information.
 * @author nilse
 */
public class ReceiptDTO {
    private final ItemListDTO itemListDTO;
    
    private final double totalCostBeforeDiscount;
    private final LocalDateTime dateAndTime;
    private final double change;
    private final double payment;
    private final double percentageAfterDiscount;
    private final double flatDiscount;
    private final double discount;
    private final double costAfterDiscount;
    private final double totalVAT;
    
    
    /**
     * Creates a receiptDTO object.
     * 
     * @param receipt A receipt the ReceiptDTO is based on.
     */
    public ReceiptDTO(Receipt receipt){
        this.totalCostBeforeDiscount = receipt.getCostBeforeDiscount();
        this.dateAndTime = receipt.getDateAndTime();
        this.change = receipt.getChange();
        this.payment = receipt.getPayment();
        this.percentageAfterDiscount = receipt.getPercentageAfterDiscount();
        this.flatDiscount = receipt.getFlatDiscount();
        this.costAfterDiscount = receipt.getCurrentCost();
        this.totalVAT = receipt.getTotalVAT();
        this.discount = receipt.getDiscount();
        
        this.itemListDTO = receipt.getItemListDTO();
    }
    
    /**
     * Gets the cost of the current sale before discounts are applied
     * 
     * @return A double representing the cost in SEK
     */
    public double getTotalCostBeforeDiscount(){
        return totalCostBeforeDiscount;
    }
    
    /**
     * Gets a copy of date and time the sale was concluded.
     * 
     * @return A LocalDateTime object representing the date and time.
     */
    public LocalDateTime getDateAndTime(){
        return dateAndTime;
    }
    
    /**
     * Gets the change that is to be given to the customer.
     * 
     * @return A double representing the change in SEK.
     */
    public double getChange(){
        return change;
    }
    
    /**
     * Gets the amount the customer handed over. 
     * 
     * @return double representing the amount the customer have paid in SEK.
     */
    public double getPayment(){
        return payment;
    }
    
    /**
     * Gets the percentage factor that represents the percentage of 
     * the cost that remains after discounts have been applied.
     * 
     * @return A double representing the percentage factor.
     */
    public double getPercentageAfterDiscount(){
        return percentageAfterDiscount;
    }
    
    /**
     * Gets the flat discount that is applied.
     * 
     * @return A double representing the flat discount applied.
     */
    public double getFlatDiscount(){
        return flatDiscount;
    }
    /**
     * Gets the total discount that is applied to the sale.
     * 
     * @return A double representing the total discount in SEK.
     */
    public double getDiscount(){
        return discount;
    }
    
    /**
     * Gets the current cost of the sale, inc vat and discounts.
     * 
     * @return A double representing the total cost in SEK.
     */
    public double getCostAfterDiscount(){
        return costAfterDiscount;
    }
    
    /**
     * Gets the total VAT of the sale.
     * 
     * @return A double representing the VAT of the sale, in SEK.
     */
    public double getTotalVAT(){
        return totalVAT;
    }
    
    /**
     * Returns an list containing the ItemDTO's
     * that are saved on the ReceiptDTO object.
     * 
     * @return An ItemDTO List
     */
    public List<ItemDTO> getItemList(){
        return itemListDTO.getList();
    }
}
