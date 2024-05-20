/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package se.kth.iv1350.seminar5.integration;

import se.kth.iv1350.seminar5.integration.dto.ItemDTO;


/**
 * Thrown when the an update to an item stock in the External Inventory System will result in a stock that is less than zero
 * @author nilse
 */
public class ItemInventoryResultLessThanZeroException extends RuntimeException {
    
    private final ItemDTO inventoryItemDTO;
    private final ItemDTO itemDTO;
    
    /**
     * Creates an ItemInventoryResultLessThanZeroException.
     * @param inventoryItemDTO The state of the inventory before the attempted change.
     * @param itemDTO The DTO that attempted to update the inventory.
     */
    public ItemInventoryResultLessThanZeroException(ItemDTO inventoryItemDTO, ItemDTO itemDTO) {
        super("External Inventory is: " + inventoryItemDTO.getItemAmount() + " and " + itemDTO.getItemAmount() + 
                " was attempted to be removed from it");
        this.inventoryItemDTO = inventoryItemDTO;
        this.itemDTO = itemDTO;
    }
    
    /**
     * Gets the state of item in the inventory system.
     * @return The ItemDTO of the inventory item.
     */
    public ItemDTO getInventoryItemDTO() {
        return inventoryItemDTO;
    }
    
    /**
     * Gets the ItemDTO that attempted to update the system.
     * @return The ItemDTO.
     */
    public ItemDTO getItemDTO() {
        return itemDTO;
    }
    
}
