/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.quickchat;

/**
 *
 * @author jerem
 */

import javax.swing.JOptionPane;
import java.util.Random;

public class QuickChat { 
    private static final Login login = new Login();
    private static StringBuilder StringBuilder;

    public static void main(String[] args) {
        JOptionPane.showMessageDialog(null, "Welcome to QuickChat.", "QuickChat", JOptionPane.INFORMATION_MESSAGE);

        // User Registration
        String firstName = JOptionPane.showInputDialog("Enter First Name:");
        String lastName = JOptionPane.showInputDialog("Enter Last Name:");
        String username = JOptionPane.showInputDialog("Enter Username (must contain _ and be <= 5 characters):");
        String password = JOptionPane.showInputDialog("Enter Password (min 8 chars, 1 capital, 1 number, 1 special):");
        String cellNumber = JOptionPane.showInputDialog("Enter Cell Number (+27xxxxxxxxx):");

        String registrationResult = login.registerUser(firstName, lastName, username, password, cellNumber);
        JOptionPane.showMessageDialog(null, registrationResult);

        if (!registrationResult.contains("successfully")) {
            System.exit(1);
        }

        // User Login
        String loginUsername = JOptionPane.showInputDialog("Enter Username:");
        String loginPassword = JOptionPane.showInputDialog("Enter Password:");
        String loginStatus = login.returnLoginStatus(loginUsername, loginPassword);
        JOptionPane.showMessageDialog(null, loginStatus);

        if (!login.isLoggedIn()) {
            System.exit(1);
        }

        // Load stored messages from JSON
        Message.loadStoredMessages();

        // Get number of messages
        int numMessages;
        try {
            numMessages =rosPane("How many messages would you like to send?");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid number. Exiting.");
            System.exit(1);
            return;
        }

        // Message sending loop
        for (int i = 0; i < numMessages; i++) {
            String messageId = generateMessageId();
            String recipient = JOptionPane.showInputDialog("Enter Recipient Cell Number (+27xxxxxxxxx):");
            String message = JOptionPane.showInputDialog("Enter Message (max 250 characters):");

            Message msg = new Message(messageId, recipient, message, "Pending");
            String result = msg.sendMessage();
            JOptionPane.showMessageDialog(null, result);
        }

        // Menu loop
        while (true) {
            String[] options = {"Send Messages", "Show Recent Messages", "Display Sent Messages", "Display Longest Message",
                    "Search by Message ID", "Search by Recipient", "Delete by Message Hash", "Display Report", "Quit"};
            int choice = JOptionPane.showOptionDialog(null, "Choose an option:", "QuickChat Menu",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

            switch (choice) {
                case 0: // Send Messages
                    int additionalMessages = Integer.parseInt(JOptionPane.showInputDialog("How many additional messages?"));
                    for (int i = 0; i < additionalMessages; i++) {
                        String messageId = generateMessageId();
                        String recipient = JOptionPane.showInputDialog("Enter Recipient Cell Number (+27xxxxxxxxx):");
                        String message = JOptionPane.showInputDialog("Enter Message (max 250 characters):");

                        Message msg = new Message(messageId, recipient, message, "Pending");
                        String result = msg.sendMessage();
                        JOptionPane.showMessageDialog(null, result);
                    }
                    break;
                case 1: // Show Recent Messages
                    JOptionPane.showMessageDialog(null, "Coming Soon.");
                    break;
                case 2: // Display Sent Messages
                    JOptionPane.showMessageDialog(null, displaySentMessages());
                    break;
                case 3: //Display Longest Message
                    JOptionPane.showMessageDialog(null, displayLongestMessage());
                    break;
                case 4: // Search by Message ID
                    String messageId = JOptionPane.showInputDialog("Enter Message ID:");
                    JOptionPane.showMessageDialog(null, searchByMessageId(messageId));
                    break;
                case 5: // Search by Recipient
                    String recipient = JOptionPane.showInputDialog("Enter Recipient Cell Number:");
                    JOptionPane.showMessageDialog(null, searchByRecipient(recipient));
                    break;
                case 6: // Delete by Message Hash
                    String messageHash = JOptionPane.showInputDialog("Enter Message Hash:");
                    JOptionPane.showMessageDialog(null, deleteByMessageHash(messageHash));
                    break;
                case 7: // Display Report
                    JOptionPane.showMessageDialog(null, displayReport());
                    break;
                case 8: // Quit
                    JOptionPane.showMessageDialog(null, "Total Messages Sent: " + Message.returnTotalMessages());
                    System.exit(0);
                    break;
            }
        }
    }

    // Generate a random 10-digit message ID
    private static String generateMessageId() {
        Random random = new Random();
        StringBuilder id = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            id.append(random.nextInt(10));
        }
        return id.toString();
    }

    // Array-based features
    public static String displaySentMessages() {
        StringBuilder result = new StringBuilder("Sent Messages:\n");
        for (Message msg : Message.getSentMessages()) {  
            result.append("Sender:").append(login.getCellNumber())
                    .append(",Recipient:").append(msg.getRecipient())
                    .append(",Message:").append(msg.getMessage()).append("\n");  
                    
        }
        return result.length() > "Sent Messages:\n".length() ? result.toString() : "No messages sent.";
    }

    public static String displayLongestMessage() {
        String longest = "";
        for (Message msg : Message.getSentMessages()) {
            if (msg.getMessage().length() > longest.length()) {
                longest = msg.getMessage();
            }
        }
        return longest.isEmpty() ? "No messages sent." : longest;
    }

    public static String searchByMessageId(String messageId) {
        for (Message msg : Message.getSentMessages()) {
            if (msg.getMessageId().equals(messageId)) {
                return "Recipient: " + msg.getRecipient() + ", Message: " + msg.getMessage();
            }
        }
        return "Message ID not found.";
    }

    public static String searchByRecipient(String recipient) {
        StringBuilder result = new StringBuilder("Messages to " + recipient + ":\n");
        for (Message msg : Message.getSentMessages()) {
            if (msg.getRecipient().equals(recipient)) {
                result.append(msg.getMessage()).append("\n");
            }
        }
        for (Message msg : Message.getStoredMessages()) {
            if (msg.getRecipient().equals(recipient)) {
                result.append(msg.getMessage()).append("\n");
            }
        }
        return result.length() > ("Messages to " + recipient + ":\n").length() ? result.toString() : "No messages found.";
    }

    public static String deleteByMessageHash(String messageHash) {
        Message toDelete = null;
        for (Message msg : Message.getSentMessages()) {
            if (msg.getMessageHash().equals(messageHash)) {
                toDelete = msg;
                break;
            }
        }
        if (toDelete != null) {
            Message.getSentMessages().remove(toDelete);
            Message.getMessageHashes().remove(messageHash);
            return "Message successfully deleted.";
        }
        return "Message hash not found.";
    }

    public static String displayReport(){
        
    StringBuilder report = new StringBuilder("Sent Messages Report:\n");
        for (Message msg : Message.getSentMessages()) {
            report.append("Message Hash: ").append(msg.getMessageHash())
                    .append(", Recipient: ").append(msg.getRecipient())
                    .append(", Message: ").append(msg.getMessage()).append("\n");
        }
        return report.length() > "Sent Messages Report:\n".length() ? report.toString() : "No messages sent.";
    }

    private static int rosPane(String how_many_messages_would_you_like_to_send) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

} 

