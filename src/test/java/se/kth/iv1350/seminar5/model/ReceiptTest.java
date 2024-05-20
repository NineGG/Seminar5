/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package se.kth.iv1350.seminar5.model;

import se.kth.iv1350.seminar5.model.Receipt;
import se.kth.iv1350.seminar5.model.InsufficientPaymentException;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import se.kth.iv1350.seminar5.integration.dto.ItemDTO;

/**
 *
 * @author nilse
 */
public class ReceiptTest {
    
    private Receipt testReceipt;
    private final ItemDTO testItemDTO;
    private final String itemName;
    private final String itemDesc;
    private final double valueAddedTax;
    private final int amount;
    private final double price;
    private final int itemId;
    
    public ReceiptTest() {
        itemDesc = "Whole Milk, 500ml, 3% Fat Content";
        itemName = "Milk";
        price = 25.50;
        valueAddedTax = 0.12;
        itemId = 423;
        amount = 2;
        
        testItemDTO = new ItemDTO(amount, itemId, itemName, itemDesc, valueAddedTax, price);
    }
    
    @BeforeEach
    public void setUp() {
        testReceipt = new Receipt();
        
    }
    
    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testAddItemToReceiptAddsItemToMatchExpextedList() {
        System.out.println("addItemToReceipt");
        List<ItemDTO> expectedList = new ArrayList<>();
        expectedList.add(new ItemDTO(
            testItemDTO.getItemAmount(), 
            testItemDTO.getItemId(), 
            testItemDTO.getItemDescription(), 
            testItemDTO.getItemName(), 
            testItemDTO.getVAT(), 
            testItemDTO.getPrice()
        ));
        try {
            testReceipt.addItemToReceipt(testItemDTO);
        } catch (Exception e) {
            fail("Threw an unexpected Exception: " + e.getMessage());
        }
        List<ItemDTO> resultList = testReceipt.getItemList();
        boolean isEqual = true;
        if (resultList.size() != expectedList.size())
            isEqual = false;
        
        int listSize = resultList.size();
        for (int i = 0; i < listSize; i++){
            if (resultList.get(i).getVAT() == expectedList.get(i).getVAT())
                isEqual = false;
            
            if (resultList.get(i).getItemAmount() == expectedList.get(i).getItemAmount())
                isEqual = false;
            
            if (resultList.get(i).getItemId() == expectedList.get(i).getItemId())
                isEqual = false;
            
            if (resultList.get(i).getItemName().equals(expectedList.get(i).getItemName()))
                isEqual = false;
            
            if (resultList.get(i).getItemDescription().equals(expectedList.get(i).getItemDescription()))
                isEqual = false;
            
            if (resultList.get(i).getPrice() == expectedList.get(i).getPrice())
                isEqual = false;
            
        }
        
        assertFalse(isEqual, "List in receipt isn't equal to the expected list");
    }

    @Test
    public void testIncreaseItemAmountEqualsExpectedIncreasedValue() {
        System.out.println("increaseItemAmount");
        try {
            testReceipt.addItemToReceipt(testItemDTO);
        } catch (Exception e) {
            fail("Threw an unexpected Exception: " + e.getMessage());
        }
        int increase = 2;
        ItemDTO increaseByItemDTO = new ItemDTO(
            increase,
            testItemDTO.getItemId(),
            testItemDTO.getItemDescription(),
            testItemDTO.getItemName(),
            testItemDTO.getVAT(),
            testItemDTO.getPrice()
        );
        try {
            testReceipt.increaseItemAmount(increaseByItemDTO);
        } catch (Exception e) {
            fail("threw an unexpected exception: " + e.getMessage());
        }
        int expectedResult = amount + increase;
        
        List<ItemDTO> testList = testReceipt.getItemList();
        int result = testList.get(0).getItemAmount();
        assertEquals(expectedResult, result, "result doesn't equal the expected increase in price");
    }

    @Test
    public void testPaymentEqualsPaidAmount() {
        System.out.println("payment");
        try {
            testReceipt.addItemToReceipt(testItemDTO);
        } catch (Exception e) {
            fail("Threw an unexpected Exception: " + e.getMessage());
        }
        
        double payment = 100.0;
        try {
            testReceipt.payment(payment);
        } catch (Exception e) {
            fail("Threw an unexpected exception: " + e.getMessage());
        }
        double result = testReceipt.getPayment();
        assertEquals(payment, result, "Payment on receipt doesn't equal the paid amount");
    }
    
    @Test public void testPaymentThrowsCorrectlyWhenPaidTooLittle() {
        System.out.println("payment exception");
        
        try {
            testReceipt.addItemToReceipt(testItemDTO);
        } catch (Exception e) {
            fail("Threw an unexpected Exception: " + e.getMessage());
        }
        double payment = 0;
        try {
            testReceipt.payment(payment);
            fail("Failed, did not throw");
        } catch (InsufficientPaymentException e) {
        } catch (Exception e) {
            fail("Threw but threw an unexpected exception");
        }
    }
    
    @Test
    public void testItemExistsGivesTheExpectedValueWhenItDoesExist() {
        System.out.println("itemExists");
        try {
            testReceipt.addItemToReceipt(testItemDTO);
        } catch (Exception e) {
            fail("Threw an unexpected Exception: " + e.getMessage());
        }
        boolean expValue = true;
        boolean result = testReceipt.itemExists(testItemDTO);
        assertEquals(expValue, result, "item not found in receipt even though it was added");
    }
    
    @Test
    public void testDiscountOnlyFlatEqualsExpected(){
        System.out.println("Discount Flat (getDiscount + addDiscountFlat)");
        try {
            testReceipt.addItemToReceipt(testItemDTO);
        } catch (Exception e) {
            fail("Threw an unexpected Exception: " + e.getMessage());
        }
        double expResult = 3.0;
        testReceipt.addDiscountFlat(expResult);
        double result = testReceipt.getDiscount();
        assertEquals(expResult, result, "Discount doesnt equal the expected value");
    }
    
    @Test
    public void testDiscountOnlyPercentageEqualsExpected() {
        System.out.println("Discount Percentage (getDiscount + addDiscountPercentage)");
        try {
            testReceipt.addItemToReceipt(testItemDTO);
        } catch (Exception e) {
            fail("Threw an unexpected Exception: " + e.getMessage());
        }
        double discountPercentage = 0.4;
        testReceipt.addDiscountPercentage(discountPercentage);
        double expResult = testReceipt.getCostBeforeDiscount() - (price * amount * (1-discountPercentage));
        double result = testReceipt.getDiscount();
        assertEquals(expResult, result, "Discount doesn't equal the expected value");
    }
    
    @Test
    public void testDiscountPrecentageAndFlatEqualsExpected(){
        System.out.println("Discount (getDiscount + addDiscountPercentage + addDiscountFlat)");
        try {
            testReceipt.addItemToReceipt(testItemDTO);
        } catch (Exception e) {
            fail("Threw an unexpected Exception: " + e.getMessage());
        }
        double discountPercentage = 0.4;
        double discountFlat = 3.0;
        double totalCost = (price * amount);
        double expResult =  totalCost - (totalCost - discountFlat) * (1 - discountPercentage);
        testReceipt.addDiscountFlat(discountFlat);
        testReceipt.addDiscountPercentage(discountPercentage);
        double result = testReceipt.getDiscount();
        assertEquals(expResult, result, "Discount doesn't equal the expected value");
    }
}
