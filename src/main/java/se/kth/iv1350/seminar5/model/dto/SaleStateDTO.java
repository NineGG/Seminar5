/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package se.kth.iv1350.seminar5.model.dto;

import se.kth.iv1350.seminar5.integration.dto.ItemDTO;

/**
 * A DTO to transfer basic sale information.
 * @author nilse
 */
public class SaleStateDTO {
    private final ItemDTO itemDTO;
    private final double runningTotal;
    private final int itemAmountChange;
    
    /**
     * Creates an SaleStateDTO
     * @param itemDTO The itemDTO to record in the SaleStateDTO.
     * @param runningTotal The running total of the sale.
     * @param itemAmountChange The amount the item changed by in the receipt.
     */
    public SaleStateDTO(ItemDTO itemDTO, double runningTotal, int itemAmountChange) {
        this.itemDTO = itemDTO;
        this.runningTotal = runningTotal;
        this.itemAmountChange = itemAmountChange;
    }
    
    /**
     * Gets the itemDTO from the SaleStateDTO.
     * @return The itemDTO.
     */
    public ItemDTO getItemDTO() {
        return this.itemDTO;
    }
    
    /**
     * Gets the running total of the sale stored in the SaleStateDTO.
     * @return The running total.
     */
    public double getRunningTotal() {
        return runningTotal;
    }
    
    /**
     * Get the amount the receipt listing of the item changed by.
     * @return The amount the item changed by.
     */
    public int getItemAmountChange() {
        return itemAmountChange;
    }
    
}
