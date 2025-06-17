/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quickchat;

import java.util.regex.Pattern;

/**
 *
 * @author jerem
 */
public class Login {



 private String cellNumber;
    private User user;


    // Check username: must contain underscore and be no more than 5 characters
    public boolean checkUsername(String username) {
        return username != null && username.contains("_") && username.length() <= 5;
    }

    // Check password: at least 8 characters, contains capital letter, number, and special character
    public boolean checkPasswordComplexity(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }
        boolean hasUpperCase = false;
        boolean hasNumber = false;
        boolean hasSpecialChar = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUpperCase = true;
            } else if (Character.isDigit(c)) {
                hasNumber = true;
            } else if (!Character.isLetterOrDigit(c)) {
                hasSpecialChar = true;
            }
        }
        return hasUpperCase && hasNumber && hasSpecialChar;
    }

    // Check cell number: must start with South Africa code (+27) and follow with 9 digits
    public boolean checkCellNumber(String cellNumber) {
        String regex = "^\\+27[0-9]{9}$";
        return cellNumber != null && Pattern.matches(regex, cellNumber);
    }

    // Register user with validations
    public String registerUser(String firstName, String lastName, String username, String password, String cellNumber) {
        StringBuilder result = new StringBuilder();

        // Validate username
        if (checkUsername(username)) {
            result.append("Username successfully captured.\n");
        } else {
            result.append("Username is not correctly formatted, please ensure it contains an underscore and is no more than 5 characters.\n");
        }

        // Validate password
        if (checkPasswordComplexity(password)) {
            result.append("Password successfully captured.\n");
        } else {
            result.append("Password is not correctly formatted, please ensure that the password contains at least 8 characters, a capital letter, a number, and a special character.\n");
        }

        // Validate cell number
        if (checkCellNumber(cellNumber)) {
            result.append("Cell number successfully added.\n");
        } else {
            result.append("Cell number incorrectly formatted or does not contain international code (+27).\n");
        }

        // If all validations pass, create user
        if (checkUsername(username) && checkPasswordComplexity(password) && checkCellNumber(cellNumber)) {
            user = new User(firstName, lastName, username, password, cellNumber);
            result.append("User successfully registered!");
        } else {
            result.append("User registration failed.");
        }

        return result.toString();
    }

    // Check login status
    public String returnLoginStatus(String username, String password) {
        if (user != null && user.getUsername().equals(username) && user.getPassword().equals(password)) {
            return String.format("Welcome %s, %s it is great to see you again!", user.getFirstName(), user.getLastName());
        } else {
            return "Username or password is incorrect, please try again.";
        }
    }

    boolean isLoggedIn() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    public String getCellNumber(){
        return cellNumber;
        
    }

}
