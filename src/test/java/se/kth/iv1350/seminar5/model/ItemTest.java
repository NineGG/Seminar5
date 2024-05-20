/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package se.kth.iv1350.seminar5.model;

import se.kth.iv1350.seminar5.model.ItemAmountOverInventoryLimitException;
import se.kth.iv1350.seminar5.model.Item;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import se.kth.iv1350.seminar5.integration.dto.ItemDTO;

/**
 * 
 * @author nilse
 */
public class ItemTest {

    private Item itemToTest;
    
    private final String itemName;
    private final String itemDescription;
    private final double valueAddedTax;
    private final int amount;
    private final double price;
    private final int itemId;
    private ItemDTO itemDTO;
    
    public ItemTest() {
        itemDescription = "Whole Milk, 500ml, 3% Fat Content";
        itemName = "Milk";
        price = 25.50;
        valueAddedTax = 0.12;
        itemId = 423;
        amount = 2;
        
        itemDTO = new ItemDTO(amount, itemId, itemName, itemDescription, valueAddedTax, price);
    }
    
    @BeforeEach
    public void setUp() {
        
        itemDTO = new ItemDTO(amount, itemId, itemName, itemDescription, valueAddedTax, price);
        
        
        try {
            itemToTest = new Item(itemDTO);
        } catch (Exception e) {
            fail("Failed to create Item: " + e.getMessage());
        }
    }
    
    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testGetItemAmount() {
        System.out.println("getItemAmount");
        int expResult = amount;
        int result = itemToTest.getItemAmount();
        assertEquals(expResult, result, "doesn't match the expected amount");
    }

    @Test
    public void testGetItemId() {
        System.out.println("getItemId");
        int expResult = itemId;
        int result = itemToTest.getItemId();
        assertEquals(expResult, result, "doesn't match the expected Id");
    }

    @Test
    public void testGetVATEqualsExpectedVAT() {
        System.out.println("getVAT");
        double expResult = valueAddedTax;
        double result = itemToTest.getVAT();
        assertEquals(expResult, result, "doesn't equal the expected VAT");
    }

    @Test
    public void testGetItemNameMatchesExpectedItemName() {
        System.out.println("getItemName");
        String expResult = itemName;
        String result = itemToTest.getItemName();
        assertEquals(expResult, result, "doesn't match the expected item name");
    }

    @Test
    public void testGetItemDescriptionMatchesExpectedDescription() {
        System.out.println("getItemDescription");
        String expResult = itemDescription;
        String result = itemToTest.getItemDescription();
        assertEquals(expResult, result, "doesn't match the expected item description");
    }

    @Test
    public void testGetPriceEqualsExpectedPrice() {
        System.out.println("getPrice");
        double expResult = price;
        double result = itemToTest.getPrice();
        assertEquals(expResult, result, "doesn't equal the expected price");
    }

    @Test
    public void testIncreaseAmountEqualsExpectedIncreasedAmount() {
        System.out.println("increaseAmount");
        int increase = 3;
        ItemDTO increaseItem = new ItemDTO(increase, itemId, itemName, itemDescription, valueAddedTax, price);;
        int expResult = amount + increase;
        try {
            itemToTest.increaseAmount(increaseItem);
        } catch (Exception e) {
            fail("threw an unexpected exception: " + e.getMessage());
        }
        int result = itemToTest.getItemAmount();
        assertEquals(expResult, result, "result doesn't equal the expected increase in price");
    }
    
    @Test
    public void testIncreaseAmountThrowsExceptionWhenTooManyItemsAdded() {
        System.out.println("increaseAmount Exception");
        
        int increase = 300;
        ItemDTO increaseItem = new ItemDTO(increase, itemId, itemName, itemDescription, valueAddedTax, price);;
        try {
            itemToTest.increaseAmount(increaseItem);
            fail("Did not throw ItemAmountOverInventoryLimitException");
        } catch (ItemAmountOverInventoryLimitException e) {
            
        } catch (Exception e) {
            fail("Threw an unexpected exception: " + e.getMessage());
        }
    }
}
