package messaging_system;

public class Message {
    private String content;
    private String senderID;
    private String recipientID;
    private String timeStamp;
    private boolean isDisappearing;

    private boolean hasRead;

    public Message(String content, String senderID, String recipientID, String timeStamp, boolean hasRead) {
        this.content = content;
        this.senderID = senderID;
        this.recipientID = recipientID;
        this.timeStamp = timeStamp;
        this.hasRead = hasRead;
        this.isDisappearing = false;
    }

    public boolean HasRead() {
        return hasRead;
    }

    public void setDisappearing(boolean disappearing) {
        isDisappearing = disappearing;
    }

    public boolean isDisappearing() {
        return this.isDisappearing;
    }

    public void setHasRead(boolean hasRead) {
        this.hasRead = hasRead;
    }

    public void editMessage(String newMessage) {
        content = newMessage;
    }

    public String getContent() {
        return content;
    }

    public String getRecipientID() {
        return recipientID;
    }

    public String getSenderID() {
        return senderID;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public static void main(String[] args) {
        Message first = new Message("Hello World", "Customer1", "Seller1", "13:09:13.103653700", false);
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
