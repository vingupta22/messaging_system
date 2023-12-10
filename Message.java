package messaging_system;

/**
 * Project 5 Option 2
 *
 * This program holds a class called message that models a message object in an e-commerce chat system.
 * Functions includes a timestamp on messages, the content of it, if its read, the sender and 
 * recipient's id, and some additional features such as if a message will be disappearing
 *
 * @author Ishaan, Nandini, Nick, Vinay, Zishuo, LO1
 *
 * @version December 10, 2023
 *
 */

public class Message {
    private String content; // created content field
    private final String senderID; // created senderID field
    private final String recipientID; // created recipientID field
    private final String timeStamp; // created timeStamp field
    private boolean isDisappearing; // created is Disappearing field for optional feature

    private boolean hasRead; // created hasRead field

    public Message(String content, String senderID, String recipientID, String timeStamp, boolean hasRead) {
        this.content = content;
        this.senderID = senderID;
        this.recipientID = recipientID;
        this.timeStamp = timeStamp;
        this.hasRead = hasRead;
        this.isDisappearing = false;
    } // Message constructor that initializes the content in the message, who is the sender and recipient by using ID,
    // when it was sent by timestamp, if its read or not, and if it wants there message to disappear after seen


    public boolean HasRead() {
        return hasRead;
    } // gets if a message have read by the recipient

    public void setDisappearing(boolean disappearing) {
        isDisappearing = disappearing;
    } // set if one want a message to disappear after read

    public boolean isDisappearing() {
        return this.isDisappearing;
    } // get if a message needs to disappear after read

    public void setHasRead(boolean hasRead) {
        this.hasRead = hasRead;
    } // set if a message have been read

    public void editMessage(String newMessage) {
        content = newMessage;
    } // edit messages function

    public String getContent() {
        return content;
    } // get the message's content

    public String getRecipientID() {
        return recipientID;
    } // get the recipient's ID

    public String getSenderID() {
        return senderID;
    } // get the sender's ID

    public String getTimeStamp() {
        return timeStamp;
    } // get the timeStamp of the message

    //main method for testing
    public static void main(String[] args) {
        Message first = new Message("Hello World", "Customer1", "Seller1",
                "13:09:13.103653700", false);
        System.out.println("Test 1 for getContent method");
        if (first.getContent().equals("Hello World")) {
            System.out.println("getContent method passed the test case");
        } else {
            System.out.println("There's an error in the getContent method");
        }
        System.out.println("Test 2 for getSenderID method");
        if (first.getSenderID().equals("Customer1")) {
            System.out.println("getSenderID method passed the test case");
        } else {
            System.out.println("There's an error in the getSenderID method");
        }
        System.out.println("Test 3 for getRecipientID method");
        if (first.getRecipientID().equals("Seller1")) {
            System.out.println("getRecipientID method passed the test case");
        } else {
            System.out.println("There's an error in the getRecipientID method");
        }
        System.out.println("Test 4 for getTimeStamp method");
        if (first.getTimeStamp().equals("13:09:13.103653700")) {
            System.out.println("getTimeStamp method passed the test case");
        } else {
            System.out.println("There's an error in the getTimeStamp method");
        }
        System.out.println("Test 5 for editMessage method");
        first.editMessage("Hello Earth");
        if (first.getContent().equals("Hello Earth")) {
            System.out.println("getContent method passed the test case");
        } else {
            System.out.println("There's an error in the getContent method");
        }
    }
}
