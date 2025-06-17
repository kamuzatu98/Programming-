/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quickchat;

/**
 *
 * @author jerem
 */
public class User {
     private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String cellNumber;

    // Constructor
    public User(String firstName, String lastName, String username, String password, String cellNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.cellNumber = cellNumber;
    }

    // Getters
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getCellNumber() {
        return cellNumber;
    }
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }
    public void setLastName(String LastName){
        this.lastName = LastName;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public void setCellNumber(String cellNumber){
        this.cellNumber = cellNumber;
    }
}
    
