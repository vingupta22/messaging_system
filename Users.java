package messaging_system;

import java.io.*;

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




}
