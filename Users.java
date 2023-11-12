package messaging_system;

import java.io.*;

import java.sql.SQLOutput;
import java.util.ArrayList;

public class Users {
    String email;
    String password;
    ArrayList<String> blockedUsers; //Changed type to String from Users
    ArrayList<String> invisibleUsers; //Changed type to String from Users
    ArrayList<Message> messagesSent;
    ArrayList<Message> messagesReceived;
    ArrayList<String> censored;
    String censorReplacement;
    Boolean haveCensor;

    public Users(String email, String password) { //Constructor
        this.email = email;
        this.password = password;
        this.blockedUsers = new ArrayList<>();
        this.invisibleUsers = new ArrayList<>();
        this.messagesSent = new ArrayList<>();
        this.messagesReceived = new ArrayList<>();
        this.censored = new ArrayList<>();
        this.censorReplacement = null;
        this.haveCensor = false;
    }

    public Boolean getHaveCensor() {
        return haveCensor;
    }

    public void setHaveCensor(Boolean haveCensor) {
        this.haveCensor = haveCensor;
    }

    public void addCensored(String censor){
        this.censored.add(censor);
    }

    public ArrayList<String> getCensored() {
        return censored;
    }

    public void setCensored(ArrayList<String> censored) {
        this.censored = censored;
    }

    public void setCensorReplacement(String censorReplacement) {
        this.censorReplacement = censorReplacement;
    }

    public String getCensorReplacement() {
        return censorReplacement;
    }

    public void hide(String hiddenUser) {
        this.invisibleUsers.add(hiddenUser);
    } //hides the user by adding it to the invisible list

    public void block(String blockUser) { //block user my adding it to the user's blocklist
        this.blockedUsers.add(blockUser);
    }

    public void deleteAccnt() { //deleting account by setting each field to null
        this.email = null;
        this.password = null;
        this.blockedUsers = null;
        this.invisibleUsers = null;
    }

    public void editAccnt(String email, String password) {
        this.setEmail(email);
        this.setPassword(password);
    } // editing account by giving parameters, which is different from the method we did in the beggining, but I'm not sure how to do it
    //without parameters


    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<String> getBlockedUsers() {
        return blockedUsers;
    }

    public ArrayList<String> getInvisibleUsers() {
        return invisibleUsers;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBlockedUsers(ArrayList<String> blockedUsers) {
        this.blockedUsers = blockedUsers;
    }

    public void setInvisibleUsers(ArrayList<String> invisibleUsers) {
        this.invisibleUsers = invisibleUsers;
    }

    // not sure how to actually send the message?
    public void sendMessage(Message msg, Users user) {
        //messageSent
        // confirmation message
        if ((this instanceof Seller && user instanceof Customer) || (this instanceof Customer && user instanceof Seller)) {
            user.messagesReceived.add(msg);
            System.out.println("Message sent!");
        } else if (this instanceof Seller) {
            System.out.println("Sorry, you cannot message another seller!");
        } else {
            System.out.println("Sorry, you cannot message another customer!");
        }

        messagesSent.add(msg);

    }

    public ArrayList<Message> getMessagesSent() {
        return messagesSent;
    }

    public ArrayList<Message> getMessagesReceived() {
        return messagesReceived;
    }

    public void setMessagesSent(ArrayList<Message> messageList){
        this.messagesSent = messageList;
    }

    public void setMessagesReceived(ArrayList<Message> messageList){
        this.messagesReceived = messageList;
    }
    public int getNumMessages() {
        return messagesSent.size();

    }

    public void deleteMessage(Message msg) {
        for (var i = 0; i < messagesSent.size(); i++) {
            if (messagesSent.get(i).equals(msg)) {
                messagesSent.remove(i);
            }
        }
    }


public static void main(String[] args){
    Users user = new Users("email@email.com", "password");
    System.out.println("Test1: Check getEmail and getPassword: ");
    if(user.getEmail().equals("email@email.com") && user.getPassword().equals("password")){
        System.out.println("Test Successful");
    } else{
        System.out.println("Test Failed: incorrect username or password");
    }


    System.out.println("");


    System.out.println("Test2: hide and getInvisibleUsers methods");
    user.hide("hiddenUser");
    ArrayList<String> invisibleUsers = user.getInvisibleUsers();
    if (invisibleUsers.size() == 1 && invisibleUsers.contains("hiddenUser")) {
        System.out.println("Test Successful");
    } else {
        System.out.println("Test failed: Invisible users not updated correctly");
    }


    System.out.println("");


    System.out.println("Test3: block and getBlockedUsers methods");
    user.block("blockedUser");
    ArrayList<String> blockedUsers = user.getBlockedUsers();
    if(!blockedUsers.isEmpty() && blockedUsers.contains("blockedUser")){
        System.out.println("Test Successful");
    } else{
        System.out.println("Test failed: blocked user not added correctly.");
    }

    System.out.println("");


    System.out.println("Test4: Checks the editAccount method");
    user.editAccnt("new@gmail.com", "newPassword");
    if(user.getEmail().equals("new@gmail.com") && user.getPassword().equals("newPassword")){
        System.out.println("Test Successful");
    } else{
        System.out.println("Test failed. There was an error editing the account");
    }

    System.out.println("");


    System.out.println("Test5: Checks the sendMessage method");
    Customer customer = new Customer("customer@gmail.com", "password");
    Seller seller = new Seller("seller@gmail.com", "password");
    Message message = new Message("testMessage", "customer@gmail.com", "seller@gmail.com", "", false);
    customer.sendMessage(message, seller);
    if(customer.getMessagesSent().size() == 1 && seller.getMessagesReceived().size() == 1){
        System.out.println("Test Successful");
    } else{
        System.out.println("Test failed: the message was either not sent, or there was an error");
    }

    System.out.println("");


    System.out.println("Test6: chekcs the delete message method");
    customer.deleteMessage(message);
    if(customer.getMessagesSent().isEmpty()){
        System.out.println("Test Successful");
    } else{
        System.out.println("Test failed: there was an error deleting the message");
    }

    System.out.println("");


    System.out.println("Test7: checks to see if the account deleted properly");
    user.deleteAccnt();
    if(user.getEmail() == null && user.getPassword() == null){
        System.out.println("Test Successful");
    } else{
        System.out.println("Test failed: there was an error with deleting the account");
    }




}

}
