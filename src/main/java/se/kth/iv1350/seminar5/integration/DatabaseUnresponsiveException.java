/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package se.kth.iv1350.seminar5.integration;

/**
 * Thrown when the client failed to connect to the database.
 * @author Nils Ekenberg
 */
public class DatabaseUnresponsiveException extends RuntimeException{

    public DatabaseUnresponsiveException() {
        super("Client Failed To Connect To The Database");
    }
    
    
}
