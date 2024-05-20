/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package se.kth.iv1350.seminar5.integration;

import se.kth.iv1350.seminar5.integration.dto.ItemDTO;

/**
 * An set of an ExternalInventorySystemItem and it's corresponding ItemDTO
 * @author nilse
 */
class UpdateQueueSet{
        
    private final ItemDTO itemDTO;
    private final ExternalInventorySystemItem inventoryItem;

    public UpdateQueueSet(ItemDTO itemDTO, ExternalInventorySystemItem inventoryItem) {
        this.itemDTO = itemDTO;
        this.inventoryItem = inventoryItem;
    }

    /**
     * Updates the inventory of the ExternalInventorySystemItem object in the set.
     * @throws ItemInventoryResultLessThanZeroException If operation results in inventory going into the negatives.
     */
    public void update() throws ItemInventoryResultLessThanZeroException {
        inventoryItem.updateInventory(itemDTO);
    }
}
