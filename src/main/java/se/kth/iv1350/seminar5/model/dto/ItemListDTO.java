/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package se.kth.iv1350.seminar5.model.dto;

import java.util.ArrayList;
import java.util.List;
import se.kth.iv1350.seminar5.integration.dto.ItemDTO;

/**
 * A DTO that contains an List containing ItemDTOs
 * @author Nils Ekenberg
 */
public class ItemListDTO {
    
    private final List<ItemDTO> itemDTOList;
    
    /**
     * creates a new ItemListDTO instance based on an List of ItemDTOs.
     * @param itemDTOList the List to copy;
     */
    public ItemListDTO(List<ItemDTO> itemDTOList) {
        this.itemDTOList = new ArrayList<>();
        for (ItemDTO item : itemDTOList) {
            this.itemDTOList.add(item);
        }
    }
    
    /**
     * Gets an copy of the List with ItemDTOs.
     * @return The List of ItemDTOs.
     */
    public List<ItemDTO> getList() {
        List<ItemDTO> returnList = new ArrayList<>();
        for (ItemDTO item : itemDTOList) {
            returnList.add(item);
        }
        
        return returnList;
    }
    
}
