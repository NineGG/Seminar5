/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package se.kth.iv1350.seminar5.controller;

/**
 * Thrown when an action failed
 * @author Nils Ekenberg
 */
public class ActionFailedException extends Exception {

    public ActionFailedException(String message ,Exception cause) {
        super(message, cause);
    }
    
    
}
