package messaging_system;

import java.io.*;
import java.util.ArrayList;

public class Users {
    String email;
    String password;
    ArrayList<Users> blockedUsers;
    ArrayList<Users> invisibleUsers;
    ArrayList<Message> messagesSent;
    ArrayList<Message> messagesReceived;

    public Users(String email, String password) { //Constructor
        this.email = email;
        this.password = password;
        this.blockedUsers = new ArrayList<>();
        this.invisibleUsers = new ArrayList<>();
    }

    public void hide(Users hiddenUser) {
        this.invisibleUsers.add(hiddenUser);
    } //hides the user by adding it to the invisible list

    public void block(Users blockUser) { //block user my adding it to the user's blocklist
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

    public ArrayList<Users> getBlockedUsers() {
        return blockedUsers;
    }

    public ArrayList<Users> getInvisibleUsers() {
        return invisibleUsers;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBlockedUsers(ArrayList<Users> blockedUsers) {
        this.blockedUsers = blockedUsers;
    }

    public void setInvisibleUsers(ArrayList<Users> invisibleUsers) {
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

    // for now, I'll be throwing all errors, but handling needs to be implemented
    public void msgHist() throws IOException {
        BufferedWriter brw = new BufferedWriter(new FileWriter("messageHistory.csv"));
        for (var i = 0; i < messagesSent.size(); i++) {
            /* Each message will be stored in a single line. The line is structured like this:
            content, senderID, recipientID, timestamp
             */
            brw.write(messagesSent.get(i).getContent() + "," + "Sender: " + messagesSent.get(i).getSenderID()
                    + "," + "Recipient: " + messagesSent.get(i).getRecipientID() + ", Time: " + messagesSent.get(i).getTimeStamp());


        }
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

    public void sendFile(String filename, Users user) throws IOException {
        String message = "";
        BufferedReader bfr = null;
        try {
            bfr = new BufferedReader(new FileReader(filename));
        } catch (FileNotFoundException e) {
            System.out.println("Sorry, file does not exist!");
        }
        String line = bfr.readLine();
        while (line != null) {
            message.concat(line + "/n");
        }

        // assuming userID is email, timestamp is set to null for now because idk how to get timestamp
        sendMessage(new Message(message, this.email, user.getEmail(), null), user);




    }


}
