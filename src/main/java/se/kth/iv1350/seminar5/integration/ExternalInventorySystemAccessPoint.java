/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package se.kth.iv1350.seminar5.integration;
import java.util.ArrayList;
import java.util.List;
import se.kth.iv1350.seminar5.integration.dto.ItemDTO;

/**
 * This class is a handler class that represents a connection 
 * to the External inventory System.
 *
 * @author nilse
 */
public class ExternalInventorySystemAccessPoint {
    
    private static final ExternalInventorySystemAccessPoint INSTANCE = 
            new ExternalInventorySystemAccessPoint();
    private final List<ExternalInventorySystemItem> inventory;
    private String itemDescription;
    private String itemName;
    private double price;
    private double valueAddedTax;
    private int itemId;
    
    /**
     * Gets the singleton instance of ExternalInventorySystemAccessPoint.
     * @return The instance.
     */
    public static ExternalInventorySystemAccessPoint getInstance(){
        return INSTANCE;
    }
    
    /**
     * initialize database
     */
    private ExternalInventorySystemAccessPoint(){
        this.inventory = new ArrayList<>();
        
        itemDescription = "Whole Milk, 500ml, 3% Fat Content";
        itemName = "Milk";
        price = 25.50;
        valueAddedTax = 0.12;
        itemId = 423;
        inventory.add(new ExternalInventorySystemItem(100, itemId, itemName, itemDescription, valueAddedTax, price));
        
        itemDescription = "500g, Gluten Free";
        itemName = "Bread";
        price = 54.50;
        valueAddedTax = 0.12;
        itemId = 231;
        inventory.add(new ExternalInventorySystemItem(100, itemId, itemName, itemDescription, valueAddedTax, price));
    }
    
    /**
     * requests an item from the ExternalInventorySystem(fake) based on the itemId provided.
     * 
     * @param itemId The id of the item requested.
     * @param itemAmount The number of said item to retrieve.
     * @return The item object.
     * @throws NoMatchingItemByIdException If no item with that id was found.
     * @throws DatabaseUnresponsiveException If the database is unresponsive.
     */
    public ItemDTO retrieveItem(int itemId, int itemAmount) throws NoMatchingItemByIdException, 
                                                            DatabaseUnresponsiveException{
        if (itemId == 123)
            throw new DatabaseUnresponsiveException();
        for (ExternalInventorySystemItem item : inventory){
            if (itemId == item.getItemId()){
                itemDescription = item.getItemDescription();
                itemName = item.getItemName();
                price = item.getPrice();
                valueAddedTax = item.getVAT();
                
                return new ItemDTO(itemAmount, itemId, itemName, itemDescription, valueAddedTax, price);
            } 
        }
        
        throw new NoMatchingItemByIdException(itemId);
    }
    
    
    public ItemDTO retrieveItemStatus(int itemId) throws NoMatchingItemByIdException, 
                                                            DatabaseUnresponsiveException{
        if (itemId == 123)
            throw new DatabaseUnresponsiveException();
        for (ExternalInventorySystemItem item : inventory){
            if (itemId == item.getItemId()){
                return new ItemDTO(item);
            } 
        }
        
        throw new NoMatchingItemByIdException(itemId);
    }
    
    /**
     * Updates the ExternalInventorySystem based on the items provided.
     * 
     * @param itemList List of Item objects.
     */
    public void updateInventory(List<ItemDTO> itemList) {
        
        List<UpdateQueueSet> queue = new ArrayList<>();
        
        for(ItemDTO item : itemList){
            for(ExternalInventorySystemItem inventoryItem : inventory){
                if (inventoryItem.getItemId() == item.getItemId()){
                    queue.add(new UpdateQueueSet(item, inventoryItem));
                    
                    if (inventoryItem.getNumOfItemInInventory() < item.getItemAmount()){
                        throw new ItemInventoryResultLessThanZeroException(
                                new ItemDTO(inventoryItem),
                                item
                        );
                    }
                }
            }
        }
        
        for (UpdateQueueSet set : queue) {
            set.update();
        }
    }
    
    

    
}
