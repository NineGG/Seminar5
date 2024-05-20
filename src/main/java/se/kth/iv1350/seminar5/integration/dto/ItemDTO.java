/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package se.kth.iv1350.seminar5.integration.dto;

import se.kth.iv1350.seminar5.integration.ExternalInventorySystemItem;

/**
 * A DTO class used to transfer Item information.
 * 
 * @author nilse
 */
public class ItemDTO {
    
    private final double valueAddedTax;
    private final double itemPrice;
    private final int itemId;
    private final String itemName;
    private final int itemAmount;
    private final String itemDescription;
    
    /**
     * Creates an ItemDTO.
     * 
     * @param itemAmount The amount of said item;
     * @param itemId The item Id
     * @param itemName The name of the item
     * @param itemDescription A simple description of the item
     * @param valueAddedTax The VAT
     * @param itemPrice The cost of the item
     */
    public ItemDTO(int itemAmount, int itemId, String itemName, String itemDescription, double valueAddedTax, double itemPrice){
        
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemAmount = itemAmount;
        this.itemDescription = itemDescription;
        this.valueAddedTax = valueAddedTax;
    }
    
    
    /**
     * Creates an ItemDTO based on an External Inventory System Item.
     * @param inventoryItem The External Inventory System Item the ItemDTO is based on.
     */
    public ItemDTO(ExternalInventorySystemItem inventoryItem){
        this.itemAmount = inventoryItem.getNumOfItemInInventory();
        this.itemPrice = inventoryItem.getPrice();
        this.itemId = inventoryItem.getItemId();
        this.itemName = inventoryItem.getItemName();
        this.itemDescription = inventoryItem.getItemDescription();
        this.valueAddedTax = inventoryItem.getVAT();
    }
    
    
    /**
     * Gets the amount of the item.
     * 
     * @return An int representing the number of said item.
     */
    public int getItemAmount(){
        return this.itemAmount;
    }
    
    /**
     * Gets the items id
     * 
     * @return An int representing the items id.
     */
    public int getItemId(){
        return this.itemId;
    }
    
    
    /**
     * Gets the VAT in a percentage factor form.
     * 
     * @return A double representing the VAT in a percentage factor form.
     */
    public double getVAT(){
        return this.valueAddedTax;
    }
    
    /**
     * Gets the items name.
     * 
     * @return A string containing the items name.
     */
    public String getItemName(){
        return this.itemName;
    }
    
    
    /**
     * Gets a description of the item.
     * 
     * @return A string containing the items description.
     */
    public String getItemDescription(){
        return this.itemDescription;
    }
    
    /**
     * Gets the items price.
     * 
     * @return An int representing the cost of one item in SEK.
     */
    public double getPrice(){
        return this.itemPrice;
    }
    
}
