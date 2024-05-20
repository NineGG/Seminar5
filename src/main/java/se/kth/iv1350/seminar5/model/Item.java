/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package se.kth.iv1350.seminar5.model;

import se.kth.iv1350.seminar5.integration.dto.ItemDTO;
import se.kth.iv1350.seminar5.integration.ExternalInventorySystemAccessPoint;

/**
 * An item object, containing information about the item.
 *
 * @author nilse
 */
public class Item {
    private int itemAmount;
    private final int itemId;
    private final double itemPrice;
    private final String itemName;
    private final String itemDescription;
    private final double valueAddedTax;
    private final ExternalInventorySystemAccessPoint inventorySystem;
    private ItemDTO itemStatus;
    

    /**
     * Creates an Item object from an ItemDTO
     * @param itemDTO The ItemDTO to create an Item from.
     * @throws ItemAmountOverInventoryLimitException If the total item count is going over what the inventory system holds.
     */
    public Item(ItemDTO itemDTO) throws ItemAmountOverInventoryLimitException{
        inventorySystem = ExternalInventorySystemAccessPoint.getInstance();
        itemStatus = inventorySystem.retrieveItemStatus(itemDTO.getItemId());
        
        if (itemDTO.getItemAmount() > itemStatus.getItemAmount()) throw new ItemAmountOverInventoryLimitException(
                itemDTO, itemDTO.getItemAmount()-itemStatus.getItemAmount());
        
        this.itemAmount = itemDTO.getItemAmount();
        this.itemPrice = itemDTO.getPrice();
        this.itemId = itemDTO.getItemId();
        this.itemName = itemDTO.getItemName();
        this.itemDescription = itemDTO.getItemDescription();
        this.valueAddedTax = itemDTO.getVAT();
    }
    
    /**
     * Gets current item amount.
     * @return The item amount.
     */
    public int getItemAmount(){
        return this.itemAmount;
    }
    
    /**
     * Gets the item identifier.
     * @return The item identifier.
     */
    public int getItemId(){
        return this.itemId;
    }
    
    /**
     * Gets the Value Added Tax (VAT).
     * @return The VAT.
     */
    public double getVAT(){
        return this.valueAddedTax;
    }
    
    /**
     * Gets the items name.
     * @return The item name
     */
    public String getItemName(){
        return this.itemName;
    }
    
    /**
     * Gets a string with descriptive tags of the item.
     * @return The item description.
     */
    public String getItemDescription(){
        return this.itemDescription;
    }
    
    /**
     * Gets the price of the item.
     * @return The price.
     */
    public double getPrice(){
        return this.itemPrice;
    }
    
    /**
     * Increases the item count.
     * @param itemDTO The ItemDTO to increase the item count with.
     * @throws ItemAmountOverInventoryLimitException If the total item count is going over what the inventory system holds
     */
    public void increaseAmount(ItemDTO itemDTO) throws ItemAmountOverInventoryLimitException {
        itemStatus = inventorySystem.retrieveItemStatus(itemDTO.getItemId());
        
        if ((itemDTO.getItemAmount() + itemAmount) > itemStatus.getItemAmount()) throw new ItemAmountOverInventoryLimitException(
        itemDTO, (itemDTO.getItemAmount() + itemAmount ) - itemStatus.getItemAmount());
        this.itemAmount += itemDTO.getItemAmount();
    }
    
    
}
