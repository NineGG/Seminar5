/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package se.kth.iv1350.seminar5.integration;


import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import se.kth.iv1350.seminar5.integration.dto.ItemDTO;
import se.kth.iv1350.seminar5.model.dto.ItemListDTO;

/**
 *
 * @author Nils Ekenberg
 */
public class ExternalInventorySystemAccessPointTest {
    
    ExternalInventorySystemAccessPoint invInstance = ExternalInventorySystemAccessPoint.getInstance();
    
    private final ItemDTO itemDTO;
    private final ItemDTO itemTwoDTO;
    private final String itemDescription;
    private final String itemName;
    private final double price;
    private final double valueAddedTax;
    private final int itemId;
    
    
    public ExternalInventorySystemAccessPointTest() {
        itemDescription = "500g, Gluten Free";
        itemName = "Bread";
        price = 54.50;
        valueAddedTax = 0.12;
        itemId = 231;
        itemDTO = new ItemDTO(110, itemId, itemName, itemDescription, valueAddedTax, price);
        itemTwoDTO = new ItemDTO(90, itemId, itemName, itemDescription, valueAddedTax, price);
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() throws ItemInventoryResultLessThanZeroException {
        List<ItemDTO> itemList = new ArrayList<>();
        ItemDTO correction;
        try{
            correction = invInstance.retrieveItemStatus(itemId);
            itemList.add(new ItemDTO(-(100-correction.getItemAmount()), itemId, itemName, itemDescription, valueAddedTax, price));
            ItemListDTO itemListDTO = new ItemListDTO(itemList);
            invInstance.updateInventory(itemListDTO);
        } catch (Exception e) {
            fail("Teardown of Unit Test Failed: " + e.getMessage());
        }
        ItemDTO check = invInstance.retrieveItemStatus(itemId);
        if (check.getItemAmount() != 100) {
            fail("Teardown of Unit Test Failed");
        }
    }
    


    @Test
    public void testUpdateInventoryUpdateEqualsExpectedValue() {
        System.out.println("updateInventory");
        List<ItemDTO> itemList = new ArrayList<>();
        itemList.add(itemTwoDTO);
        try {
            int expected = invInstance.retrieveItemStatus(itemTwoDTO.getItemId()).getItemAmount() - itemTwoDTO.getItemAmount();
            ItemListDTO itemListDTO = new ItemListDTO(itemList);
            invInstance.updateInventory(itemListDTO);
            int result = invInstance.retrieveItemStatus(itemTwoDTO.getItemId()).getItemAmount();
            assertEquals(expected, result, "Did not update correctly, expected " + expected + " got " + result);
        } catch (Exception e) {
            fail("Threw an unexpected exception: " + e.getMessage());
        }
    }

    
    @Test
    public void testUpdateInventoryThrowsItemInventoryResultLessThanZeroExceptionCorrectly() {
        System.out.println("updateInventory Exception");
        
        List<ItemDTO> itemList = new ArrayList<>();
        itemList.add(itemDTO);
        ItemListDTO itemListDTO = new ItemListDTO(itemList);
        
        try {
            invInstance.updateInventory(itemListDTO);
            fail("updateInvntory fails to throw when inventory is about to go into the negatives.");
        } catch (ItemInventoryResultLessThanZeroException e) {
        } catch (Exception e) {
            fail("Threw an unexpected exception: " + e.getMessage());
        }
    }
    
    @Test
    public void testRetrieveItemThrowsNoMatchingItemByIdExceptionCorrectlyWhenGivenAnInvalidId() {
        System.out.println("retrieveItem NoMatchingItemByIdException");
        
        int id = 5;
        try {
            invInstance.retrieveItem(5, 1);
            fail("Did not throw NoMatchingItemByIdException as expected when gived Id: " + id);
        } catch (NoMatchingItemByIdException e) {
            
        } catch (Exception e) {
            fail("Threw an unexpected exception"); 
        }
    }
    
    @Test
    public void testRetrieveItemThrowsDatabaseUnresponsiveExceptionCorrectlyWhenGivenCorrectId() {
        System.out.println("retrieveItem DatabaseUnresponsiveException");
        
        try {
            invInstance.retrieveItem(123, 1);
            fail("Did not throw DatabaseUnresponsiveException as expected");
        } catch (DatabaseUnresponsiveException e) {
            
        } catch (Exception e) {
            fail("Threw an unexpected exception: " + e.getMessage()); 
        }
    }
    
    @Test
    public void testRetrieveItemGetsItemWithCorrectId() {
        System.out.println("retrieveItem");
        int result;
        try {
            result = invInstance.retrieveItem(itemId, 1).getItemId();
            assertEquals(result, itemId, "Did not get an item with the correct id, expected " + itemId + " got: " + result);
        } catch (Exception e) {
            fail("Threw an unexpected exception: " + e.getMessage());
        }
    }
    
    @Test
    public void testRetrieveItemStatusGetsItemWithCorrectId() {
        System.out.println("retrieveItemStatus");
        int result;
        try {
            result = invInstance.retrieveItemStatus(itemId).getItemId();
            assertEquals(result, itemId, "Did not get an item with the correct id, expected " + itemId + " got: " + result);
        } catch (Exception e) {
            fail("Threw an unexpected exception: " + e.getMessage());
        }
    }
    
    @Test
    public void testRetrieveItemStatusThrowsNoMatchingItemByIdExceptionCorrectlyWhenGivenAnInvalidId(){
        System.out.println("retrieveItemStatus NoMatchingItemByIdException");
        
        int id = 5;
        try {
            invInstance.retrieveItemStatus(5);
            fail("Did not throw NoMatchingItemByIdException as expected when gived Id: " + id);
        } catch (NoMatchingItemByIdException e) {
            
        } catch (Exception e) {
            fail("Threw an unexpected exception"); 
        }
    }
    
    @Test
    public void testRetrieveItemStatusThrowsDatabaseUnresponsiveExceptionCorrectlyWhenGivenCorrectId() {
        System.out.println("retrieveItemStatus DatabaseUnresponsiveException");
        
        try {
            invInstance.retrieveItemStatus(123);
            fail("Did not throw DatabaseUnresponsiveException as expected");
        } catch (DatabaseUnresponsiveException e) {
            
        } catch (Exception e) {
            fail("Threw an unexpected exception: " + e.getMessage()); 
        }
    }
    
}
