package messaging_system;

import java.util.ArrayList;

/**
 * Project 5 Option 2
 *
 * This program holds a class called Users that models a type of user in an e-commerce system
 * and is a template for the Seller and Customer classes. This contains all the basic functions of a user
 * Such as key components of creating an account, message sent,receive, and censored text, and a list of blocked and
 * invisible users
 *
 * @author Ishaan, Nandini, Nick, Vinay, Zishuo, LO1
 *
 * @version December 10, 2023
 *
 */

public class Users {
    String email; // email field
    String password; // password email
    ArrayList<String> blockedUsers; //Changed type to String from Users, blocked users list field
    ArrayList<String> invisibleUsers; //Changed type to String from Users, invisible users list field
    ArrayList<Message> messagesSent;// create message sent list field
    ArrayList<Message> messagesReceived;// create message received list field
    ArrayList<String> censored;// create censor message list field
    String censorReplacement;// create censor replacement
    Boolean haveCensor;// havecensor field

    public Users(String email, String password) {
        this.email = email;
        this.password = password;
        this.blockedUsers = new ArrayList<>();
        this.invisibleUsers = new ArrayList<>();
        this.messagesSent = new ArrayList<>();
        this.messagesReceived = new ArrayList<>();
        this.censored = new ArrayList<>();
        this.censorReplacement = null;
        this.haveCensor = false;
    } // User constructor that allows them to initialize email, password which relates to account creating,
    // then some preference settings such as blocking and invisible user lists, message sent and recieved
    // by the user, and an arraylist to see if the user wants to censor phrases/words, and the replacemnt
    // of those censored

    public Boolean getHaveCensor() {
        return haveCensor;
    } // check if a user has a censored word/phrase

    public void setHaveCensor(Boolean haveCensor) {
        this.haveCensor = haveCensor;
    } // set if a user wants to have censored or not

    public void addCensored(String censor) {
        this.censored.add(censor);
    } // add censor word/phrase onto the list

    public ArrayList<String> getCensored() {
        return censored;
    } // get the censored arraylist

    public void setCensored(ArrayList<String> censored) {
        this.censored = censored;
    } //set string that wants to be censored

    public void setCensorReplacement(String censorReplacement) {
        this.censorReplacement = censorReplacement;
    } // set the replacement word/phrase once done, if doesnt want default censor symbols

    public String getCensorReplacement() {
        return censorReplacement;
    } // get the censor replacement

    public void hide(String hiddenUser) {
        this.invisibleUsers.add(hiddenUser);
    } //hides the user by adding it to the invisible list

    public void block(String blockUser) { 
        this.blockedUsers.add(blockUser);
    } // adds a user to block list 

    public void deleteAccnt() { 
        this.email = null;
        this.password = null;
        this.blockedUsers = null;
        this.invisibleUsers = null;
    } //deleting account by setting each field to null

    public void editAccnt(String email, String password) {
        this.setEmail(email);
        this.setPassword(password);
    } // editing accounts


    public String getEmail() {
        return email;
    } // gets user email

    public String getPassword() {
        return password;
    }// gets user password

    public ArrayList<String> getBlockedUsers() {
        return blockedUsers;
    } // gets user's blocked users list

    public ArrayList<String> getInvisibleUsers() {
        return invisibleUsers;
    } // gets user's invisible users list

    public void setEmail(String email) {
        this.email = email;
    } // change users email

    public void setPassword(String password) {
        this.password = password;
    } // change users password

    public void setBlockedUsers(ArrayList<String> blockedUsers) {
        this.blockedUsers = blockedUsers;
    } // change blocked users list

    public void setInvisibleUsers(ArrayList<String> invisibleUsers) {
        this.invisibleUsers = invisibleUsers;
    } // change invisible users list

    // not sure how to actually send the message?
    public String sendMessage(Message msg, Users user) {
        String confirm = "";
        //messageSent
        // confirmation message
        if ((this instanceof Seller && user instanceof Customer) || (this instanceof Customer && user
                instanceof Seller)) {
            user.messagesReceived.add(msg);

            confirm = "Message sent!";
        } else if (this instanceof Seller) {
            confirm = "Sorry, you cannot message another seller!";
        } else {
            confirm = "Sorry, you cannot message another customer!";
        }

        messagesSent.add(msg);
        return confirm;
    } // send message method

    public ArrayList<Message> getMessagesSent() {
        return messagesSent;
    } // get if message is sent

    public ArrayList<Message> getMessagesReceived() {
        return messagesReceived;
    } // get if message is received

    public void setMessagesSent(ArrayList<Message> messageList) {
        this.messagesSent = messageList;
    }// set if a message has been sent

    public void setMessagesReceived(ArrayList<Message> messageList) {
        this.messagesReceived = messageList;
    }// set if a message has been received

    public int getNumMessages() {
        return messagesSent.size();

    } // get number of messages sent

    public void deleteMessage(Message msg) {
        for (var i = 0; i < messagesSent.size(); i++) {
            if (messagesSent.get(i).equals(msg)) {
                messagesSent.remove(i);
            }
        }
    } // delete message method 

    //main method for testing
    public static void main(String[] args) {
        Users user = new Users("email@email.com", "password");
        System.out.println("Test1: Check getEmail and getPassword: ");
        if (user.getEmail().equals("email@email.com") && user.getPassword().equals("password")) {
            System.out.println("Test Successful");
        } else {
            System.out.println("Test Failed: incorrect username or password");
        }


        System.out.println();


        System.out.println("Test2: hide and getInvisibleUsers methods");
        user.hide("hiddenUser");
        ArrayList<String> invisibleUsers = user.getInvisibleUsers();
        if (invisibleUsers.size() == 1 && invisibleUsers.contains("hiddenUser")) {
            System.out.println("Test Successful");
        } else {
            System.out.println("Test failed: Invisible users not updated correctly");
        }


        System.out.println();


        System.out.println("Test3: block and getBlockedUsers methods");
        user.block("blockedUser");
        ArrayList<String> blockedUsers = user.getBlockedUsers();
        if (!blockedUsers.isEmpty() && blockedUsers.contains("blockedUser")) {
            System.out.println("Test Successful");
        } else {
            System.out.println("Test failed: blocked user not added correctly.");
        }

        System.out.println();


        System.out.println("Test4: Checks the editAccount method");
        user.editAccnt("new@gmail.com", "newPassword");
        if (user.getEmail().equals("new@gmail.com") && user.getPassword().equals("newPassword")) {
            System.out.println("Test Successful");
        } else {
            System.out.println("Test failed. There was an error editing the account");
        }

        System.out.println();


        System.out.println("Test5: Checks the sendMessage method");
        Customer customer = new Customer("customer@gmail.com", "password");
        Seller seller = new Seller("seller@gmail.com", "password");
        Message message = new Message("testMessage", "customer@gmail.com", "seller@gmail.com",
                "", false);
        customer.sendMessage(message, seller);
        if (customer.getMessagesSent().size() == 1 && seller.getMessagesReceived().size() == 1) {
            System.out.println("Test Successful");
        } else {
            System.out.println("Test failed: the message was either not sent, or there was an error");
        }

        System.out.println();


        System.out.println("Test6: chekcs the delete message method");
        customer.deleteMessage(message);
        if (customer.getMessagesSent().isEmpty()) {
            System.out.println("Test Successful");
        } else {
            System.out.println("Test failed: there was an error deleting the message");
        }

        System.out.println();


        System.out.println("Test7: checks to see if the account deleted properly");
        user.deleteAccnt();
        if (user.getEmail() == null && user.getPassword() == null) {
            System.out.println("Test Successful");
        } else {
            System.out.println("Test failed: there was an error with deleting the account");
        }


    }

}
