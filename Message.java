package messaging_system;

public class Message {
    private String content;
    private String senderID;
    private String recipientID;
    private String timeStamp;
    private boolean isDisappearing;

    public Message(String content, String senderID, String recipientID, String timeStamp) {
        this.content = content;
        this.senderID = senderID;
        this.recipientID = recipientID;
        this.timeStamp = timeStamp;
        this.isDisappearing = false;
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

    public boolean isDisappearing() {
        return this.isDisappearing;
    }

    public void setDisappearing(boolean disappearing) {
        this.isDisappearing = disappearing;
    }
}
