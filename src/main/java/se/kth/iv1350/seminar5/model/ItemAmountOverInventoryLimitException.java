/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package se.kth.iv1350.seminar5.model;

import se.kth.iv1350.seminar5.integration.dto.ItemDTO;

/**
 * Thrown when attempting to add more items to sale than what the external inventory allows.
 * @author Nils Ekenberg
 */
public class ItemAmountOverInventoryLimitException extends Exception {
    
    private final ItemDTO itemDTO;
    private final int overTheMax;
    
    /**
     * creates a new ItemAmountOverInventoryLimitException instance
     * @param itemDTO the itemDTO that tried to add more items than allowed.
     * @param overTheMax The amount over the allowed number of items.
     */
    public ItemAmountOverInventoryLimitException(ItemDTO itemDTO, int overTheMax) {
        super("attempted to add " + itemDTO.getItemAmount() + " " + itemDTO.getItemName() + "(s)" + 
                ", which would result in " + overTheMax + " over what the inventory allows.");
        
        this.itemDTO = itemDTO;
        this.overTheMax = overTheMax;
    }
    
    /**
     * Gets the ItemDTO that tried to add more items than allowed.
     * @return The ItemDTO.
     */
    public ItemDTO getItemDTO() {
        return itemDTO;
    }
    
    /**
     * Gets how much more than the allowed amount was attempted to be added.
     * @return The amount.
     */
    public int getOverTheMax() {
        return overTheMax;
    }
    
    
}
