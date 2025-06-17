/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/EmptyTestNGTest.java to edit this template
 */
package com.mycompany.quickchat;

import java.util.Random;
import javax.swing.JOptionPane;
import org.junit.Before;
import org.junit.Test;
import static org.testng.Assert.*;



/**
 *
 * @author jerem
 */
public class QuickChatNGTest {
    private Login login;
    private Message Message;
    
 
    @Before
    public void setUp() {
        login = new Login();
        login.registerUser("John", "Doe", "j_doe", "Password123!", "+27834567890");
        
        Message  = new Message("1234567890", "+27834557896 ","Did you get the cake?","Sent");
        Message  = new Message("2234567890", "+27838884567", "Where are you? You are late! i have asked you to be one time","Stored");
        Message  = new Message("3234567890", "+27834484567", "Yohoo, I am at your gate.", "Disregard");
        Message  = new Message("4234567890", "0838884567","it is dinner time!", "Sent");
        Message  = new Message("5234567890", "+27838884567", "ok, I am leaving without you", "Stored");
        
         
        
         
       Message.loadStoredMessages();
    }
        
        
   
    @Test
    public void testSentMessagesArray(){
    String expected = """
                      Sender:+27834567890,Recipient: +27834557896, Message: Did you get the cake
                      Sender:+27834567890,Recipient:+27838884567, it is dinner time
                      """;
    assertEquals(expected, QuickChat.displayLongestMessage());

    }
    @Test
    public void testLongestMessage(){
       String expected  ="Where are you? You are late  I have ask you to on time.";
       assertEquals(expected,QuickChat.displayLongestMessage());
    }
    @Test
    public void testSearchByMessageId(){
        String expected = "Recepient:+27838884567,Message: it is dinner time!";
        assertEquals(expected,QuickChat.searchByMessageId("4234567890"));
         
    }
    @Test
    public void testSearchByRecipient(){
        String expected = """
                          Message to +27838884567:
                          Where are you? You are late! I have asked you to be on time.
                          it is dinner time
                          ok, I am leaving without you.
                          """;
        assertEquals(expected,QuickChat.searchByRecipient("+278388884567"));
                
    }
    @Test
    public void testDeleteByMessageHash(){
        String hash = "42:2|TIME";//Adjust on actual hash generated
        String expected = "Message\"it is dinner time!\"succesfully deleted,";
        assertEquals(expected,QuickChat.deleteByMessageHash(hash));
        assertFalse(Message.getMessageHashes().contains(hash));
    }
    @Test
    public void testDisplayReport(){
        String expected = """
                          Sent Messages Report:
                          Message Hash:12:1DIDGET,Recepient: +27834557896,Message: Did you get the cake?
                          Message Hash:42:2IT TIME,Recipient:+27838884567,Message:it is dinner time
                          """;
        assertEquals(expected,QuickChat.displayLongestMessage());
                
    }

    }  