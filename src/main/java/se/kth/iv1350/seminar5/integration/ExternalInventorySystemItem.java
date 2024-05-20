/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package se.kth.iv1350.seminar5.integration;

import se.kth.iv1350.seminar5.integration.dto.ItemDTO;


/**
 * An database class that stores information and keeps track of an items inventory.
 * @author nilse
 */
public class ExternalInventorySystemItem {
    private int numOfItemInInventory;
    private final int itemId;
    private final double itemPrice;
    private final String itemName;
    private final String itemDescription;
    private final double valueAddedTax;
    
    /**
     * Creates an item object
     * @param amount The amount of said item;
     * @param itemId The item Id
     * @param itemName The name of the item
     * @param itemDescription A simple description of the item
     * @param valueAddedTax The VAT
     * @param itemPrice The cost of the item
     */
    
    public ExternalInventorySystemItem(int amount, int itemId, String itemName, String itemDescription, double valueAddedTax, double itemPrice){
        this.numOfItemInInventory = amount;
        this.itemPrice = itemPrice;
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.valueAddedTax = valueAddedTax;
    }
    
    /**
     * Gets current item inventory status.
     * @return The item inventory amount.
     */
    public int getNumOfItemInInventory(){
        return this.numOfItemInInventory;
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
     * Updates inventory of the inventoryitem.
     * @param itemDTO An itemDTO of said item.
     */
    public void updateInventory(ItemDTO itemDTO) throws ItemInventoryResultLessThanZeroException {
        this.numOfItemInInventory -= itemDTO.getItemAmount();
    }
}
