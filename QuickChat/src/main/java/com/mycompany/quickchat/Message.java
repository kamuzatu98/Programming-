/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quickchat;

/**
 *
 * @author jerem
 */

    
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.swing.JOptionPane;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public final class Message {
    private final String messageId;
    private final String recipient;
    private final String message;
    private final String messageHash;
    private String flag; // Sent, Stored, or Disregard
    private static int totalMessagesSent = 0;
    private static final ArrayList<Message> sentMessages = new ArrayList<>();
    private static final ArrayList<Message> disregardedMessages = new ArrayList<>();
    private static final ArrayList<Message> storedMessages = new ArrayList<>();
    private static final ArrayList<String> messageHashes = new ArrayList<>();
    private static final ArrayList<String> messageIds = new ArrayList<>();

    public Message(String messageId, String recipient, String message, String flag) {
        this.messageId = messageId;
        this.recipient = recipient;
        this.message = message;
        this.flag = flag;
        this.messageHash = createMessageHash();
        switch (flag) {
            case "Sent":
                sentMessages.add(this);
                totalMessagesSent++;
                break;
            case "Disregard":
                disregardedMessages.add(this);
                break;
            case "Stored":
                storedMessages.add(this);
                storeMessage();
                break;
            default:
                break;
        }
        messageHashes.add(messageHash);
        messageIds.add(messageId);
    }

    // Check if message ID is exactly 10 digits
    public boolean checkMessageId() {
        return messageId != null && messageId.matches("\\d{10}");
    }

    // Check if recipient cell number starts with +27 and is 12 digits
    public boolean checkRecipientCell() {
        return recipient != null && recipient.matches("^\\+27\\d{9}$");
    }

    // Create message hash: first 2 digits of message ID, colon, message count, first and last words
    public String createMessageHash() {
        String[] words = message.trim().split("\\s+");
        String firstWord = words.length > 0 ? words[0] : "";
        String lastWord = words.length > 1 ? words[words.length - 1] : firstWord;
        String hash = messageId.substring(0, 2) + ":" + totalMessagesSent + firstWord + lastWord;
        return hash.toUpperCase();
    }

    // Handle message actions: send, store, or disregard
    public String sendMessage() {
        if (message.length() > 250) {
            return "Please enter a message of less than 250 characters.";
        }
        if (!checkRecipientCell()) {
            return "Recipient cell number is invalid.";
        }
        String[] options = {"Send Message", "Disregard Message", "Store Message"};
        int choice = JOptionPane.showOptionDialog(null, "Choose an action:", "Message Options",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        switch (choice) {
            case 0: // Send
                this.flag = "Sent";
                sentMessages.add(this);
                totalMessagesSent++;
                messageHashes.add(messageHash);
                messageIds.add(messageId);
                displayMessageDetails();
                return "Message sent.";
            case 1: // Disregard
                this.flag = "Disregard";
                disregardedMessages.add(this);
                messageHashes.add(messageHash);
                messageIds.add(messageId);
                return "Message disregarded.";
            case 2: // Store
                this.flag = "Stored";
                storedMessages.add(this);
                storeMessage();
                messageHashes.add(messageHash);
                messageIds.add(messageId);
                return "Message stored.";
            default:
                return "No action taken.";
        }
    }

    // Display message details
    private void displayMessageDetails() {
        String details = "Message ID: " + messageId + "\n" +
                "Message Hash: " + messageHash + "\n"+
                "Message: " + message;
        JOptionPane.showMessageDialog(null, details, "Message Details", JOptionPane.INFORMATION_MESSAGE);
    }

    // Store message in JSON file
    @SuppressWarnings("unchecked")
    public void storeMessage() {
        JSONObject messageObj = new JSONObject();
        messageObj.put("messageId", messageId);
        messageObj.put("recipient", recipient);
        messageObj.put("message", message);
        messageObj.put("messageHash", messageHash);
        messageObj.put("flag", flag);

        JSONArray messageList;
        try (FileReader reader = new FileReader("messages.json")) {
            JSONParser parser = new JSONParser();
            messageList = (JSONArray) parser.parse(reader);
        } catch (Exception e) {
            messageList = new JSONArray();
        }

        messageList.add(messageObj);
        try (FileWriter file = new FileWriter("messages.json")) {
            file.write(messageList.toJSONString());
            file.flush();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error storing message: " + e.getMessage());
        }
    }

    // Read stored messages from JSON file
    @SuppressWarnings("unchecked")
    public static void loadStoredMessages() {
        try (FileReader reader = new FileReader("messages.json")) {
            JSONParser parser = new JSONParser();
            JSONArray messageList = (JSONArray) parser.parse(reader);
            storedMessages.clear();
            for (Object obj : messageList) {
                JSONObject jsonMsg = (JSONObject) obj;
                String id = (String) jsonMsg.get("messageId");
                String recipient = (String) jsonMsg.get("recipient");
                String message = (String) jsonMsg.get("message");
                String flag = (String) jsonMsg.get("flag");
                if (flag.equals("Stored")) {
                    storedMessages.add(new Message(id, recipient, message, flag));
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error loading stored messages: " + e.getMessage());
        }
    }

    // Getters for arrays
    public static ArrayList<Message> getSentMessages() {
        return sentMessages;
    }

    public static ArrayList<Message> getDisregardedMessages() {
        return disregardedMessages;
    }

    public static ArrayList<Message> getStoredMessages() {
        return storedMessages;
    }

    public static ArrayList<String> getMessageHashes() {
        return messageHashes;
    }

    public static ArrayList<String> getMessageIds() {
        return messageIds;
    }

    // Getters for message fields
    public String getMessageId() {
        return messageId;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMessage() {
        return message;
    }

    public String getMessageHash() {
        return messageHash;
    }

    public static int returnTotalMessages() {
        return totalMessagesSent;
    }

    // Print all sent messages
    public static String printMessages() {
        StringBuilder result = new StringBuilder();
        for (Message msg : sentMessages) {
            result.append("Message ID: ").append(msg.messageId)
                    .append(", Recipient: ").append(msg.recipient)
                    .append(", Message: ").append(msg.message).append("\n");
        }
        return result.toString();
    }
}
