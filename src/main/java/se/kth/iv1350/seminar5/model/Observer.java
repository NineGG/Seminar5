/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package se.kth.iv1350.seminar5.model;

/**
 * An observer interface
 * @author Nils Ekenberg
 */
public interface Observer {
    
    /**
     * Updates observers with the total revenue made from sale, to be made when transaction ends.
     * @param totalRevenueFromSale The total revenue.
     */
    public void updateTotalRevenue(double totalRevenueFromSale);
    
}
