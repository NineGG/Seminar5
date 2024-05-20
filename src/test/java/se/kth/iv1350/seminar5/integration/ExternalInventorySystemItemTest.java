/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package se.kth.iv1350.seminar5.integration;

import se.kth.iv1350.seminar5.integration.ExternalInventorySystemItem;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import se.kth.iv1350.seminar5.integration.dto.ItemDTO;

/**
 *
 * @author Nils Ekenberg
 */
public class ExternalInventorySystemItemTest {
    private ExternalInventorySystemItem invItem;
    private final ItemDTO testItemDTO;
    private static final int ITEM_ONE_AMOUNT = 10;
    private static final int ITEM_TWO_AMOUNT = 20;
    private static final int ITEM_ONE_ID = 1;
    private static final int ITEM_TWO_ID = 2;
    private static final double ITEM_ONE_PRICE = 25;
    private static final double ITEM_TWO_PRICE = 5;
    private static final double ITEM_ONE_VAT = 0.25;
    private static final double ITEM_TWO_VAT = 0.12;
    
    
    
    // int itemAmount, int itemId, String itemName, String itemDescription, double valueAddedTax, double itemPrice
    public ExternalInventorySystemItemTest() {
        testItemDTO = new ItemDTO(ITEM_ONE_AMOUNT, ITEM_ONE_ID, "Banana", "", ITEM_ONE_VAT, ITEM_ONE_PRICE);
    }
    
    
    @BeforeEach
    public void setUp() {
        invItem = new ExternalInventorySystemItem(ITEM_TWO_AMOUNT, ITEM_TWO_ID, "Candy", "", ITEM_TWO_VAT, ITEM_TWO_PRICE);
    }
    
    @AfterEach
    public void tearDown() {
    }

    

    @Test
    public void testUpdateInventorySubtractsCorrectly() {
        System.out.println("updateInventory");
        try {
            invItem.updateInventory(testItemDTO);
        } catch (Exception e) {
            fail("Threw an unexpected exception.");
        }
        int expectedValue = ITEM_TWO_AMOUNT - ITEM_ONE_AMOUNT;
        assertEquals(expectedValue, invItem.getNumOfItemInInventory(), "updateInventory returns an unexpected value.");
    }
}
