/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package se.kth.iv1350.seminar5.model;
import java.time.LocalDateTime;
import java.util.*;
import se.kth.iv1350.seminar5.integration.dto.ItemDTO;
import se.kth.iv1350.seminar5.model.dto.ReceiptDTO;
import se.kth.iv1350.seminar5.model.dto.SaleStateDTO;

/**
 * A receipt containing all the information of the Sale.
 * 
 * @author nilse
 */
public class Receipt {
    
    private final List<Item> itemList;
    
    private double totalCostBeforeDiscount = 0;
    private LocalDateTime dateAndTime;
    private double change = 0;
    private double payment = 0;
    private double percentageAfterDiscount = 1;
    private double flatDiscount = 0;
    private double discount = 0;
    private double costAfterDiscount = 0;
    private double totalVAT = 0;

    
    /**
     * Creates a new Receipt instance
     */
    public Receipt() {
        this.itemList = new ArrayList<>();
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
     * @return The flat discount.
     */
    public double getFlatDiscount(){
        return flatDiscount;
    }
    
    /**
     * Gets the total discount that is applied to the sale.
     * 
     * @return A double representing the total diecount in SEK.
     */
    public double getDiscount(){
        return discount;
    }
    
    /**
     * Gets the cost of the current sale before discounts are applied
     * 
     * @return A double representing the cost in SEK
     */
    public double getCostBeforeDiscount(){
        return totalCostBeforeDiscount;
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
     * Adds an item to the receipt.
     * 
     * @param Item an item object.
     * @throws ItemAmountOverInventoryLimitException If item amount attempts to go above allowed Inventory limit.
     */
    SaleStateDTO addItemToReceipt(ItemDTO itemDTO) throws ItemAmountOverInventoryLimitException{
        itemList.add(new Item(itemDTO));
        recalculateReceipt();
        
        return new SaleStateDTO(itemDTO, costAfterDiscount, itemDTO.getItemAmount());
    }
    
    
    /**
     * increases the amount of an item.
     * 
     * @param itemId Id of said item.
     * @param increaseAmount The amount to increase the item by.
     * @return a DTO containing the updated item and running total of the sale.
     */
    SaleStateDTO increaseItemAmount(ItemDTO itemDTO) throws ItemAmountOverInventoryLimitException{
        int itemId = itemDTO.getItemId();
        for (Item item : itemList){
            if (item.getItemId() == itemId) {
                item.increaseAmount(itemDTO);
                recalculateReceipt();
                
                return new SaleStateDTO(itemDTOCreator(item), costAfterDiscount, itemDTO.getItemAmount());
            }
        }
        return null;
    }
    
    
    /**
     * checks if an item exists in the current receipt instance.
     * 
     * @param itemDTO An itemDTO of the item to look for.
     * @return A boolean representing if the item exists or not.
     */
    public boolean itemExists(ItemDTO itemDTO){
        int itemId = itemDTO.getItemId();
        for (Item itemInList : itemList){

            if (itemId == itemInList.getItemId()){
                return true;
            }
            
        }
        return false;
    }
    
    /**
     * Adds the paid amount to the receipt.
     * 
     * @param payment The paid amount in SEK.
     * @throws InsufficientPaymentException When payment is insufficient.
     */
    void payment(double payment) throws InsufficientPaymentException{
        
        if (payment < this.costAfterDiscount) 
            throw new InsufficientPaymentException(payment, this.costAfterDiscount);
        
        this.payment = payment;
        this.change = payment - this.costAfterDiscount;
        
        dateAndTime = LocalDateTime.now();
    }
    
    /**
     * Returns an list containing ItemDTO's representing the items 
     * that are saved on the Receipt object.
     * 
     * @return An ItemDTO List
     */
    public List<ItemDTO> getItemList(){
        List<ItemDTO> itemDTOList = new ArrayList<>();
        ItemDTO convertedItem;
        for (Item item : itemList){
            convertedItem = new ItemDTO(
                    item.getItemAmount(), 
                    item.getItemId(), 
                    item.getItemName(), 
                    item.getItemDescription(), 
                    item.getVAT(), 
                    item.getPrice()
            );
            itemDTOList.add(convertedItem);
        }
        return itemDTOList;
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
     * Gets the current cost of the sale, inc vat and discounts.
     * 
     * @return Double representing the total cost in SEK.
     */
    public double getCurrentCost(){
        return costAfterDiscount;
    }
    
    /**
     * Gets the date and time the sale was concluded.
     * 
     * @return A LocalDateTime object.
     */
    public LocalDateTime getDateAndTime(){
        return dateAndTime;
    }
    
    
    /**
     * Adds a percentage based discount to the sale.
     * 
     * @param discount A percentage factor representing the discount to be applied
     */
    void addDiscountPercentage(double discount){
        this.percentageAfterDiscount = this.percentageAfterDiscount * (1-discount);
        calculateDiscount();
    }
    
    
    /**
     * Adds a flat discount to the sale.
     * 
     * @param discount A flat discount to be applied.
     */
    void addDiscountFlat(double discount){
        this.flatDiscount += discount;
        calculateDiscount();
    }
    
    private void calculateDiscount(){
        costAfterDiscount = (totalCostBeforeDiscount - flatDiscount) * percentageAfterDiscount;
        discount = totalCostBeforeDiscount - costAfterDiscount;
    }
    
    
    private void recalculateReceipt(){
        double costTemp = 0;
        double tempVAT = 0;
        double itemPrice;
        double tempItemVAT;
        int itemAmount;
        for (Item item : itemList) {
            itemPrice = item.getPrice();
            itemAmount = item.getItemAmount();
            tempItemVAT = item.getVAT();
            
            costTemp += itemPrice * itemAmount;
            
            
            tempVAT += itemPrice * tempItemVAT * itemAmount;
            
        }
        totalCostBeforeDiscount = costTemp;
        totalVAT = tempVAT;
        calculateDiscount();
    }
    
    private ItemDTO itemDTOCreator(Item item){
        return new ItemDTO(
            item.getItemAmount(), 
            item.getItemId(), 
            item.getItemName(), 
            item.getItemDescription(), 
            item.getVAT(), 
            item.getPrice()
        );
    }
    
    
    /**
     * Gets an DTO of the receipt.
     * @return The ReceiptDTO.
     */
    public ReceiptDTO getReceiptDTO(){
        return new ReceiptDTO(this);
    }
    
}
