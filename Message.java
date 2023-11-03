package messaging_system;

public class Message {
    private String content;
    private String senderID;
    private String recipientID;
    private String timeStamp;

    public Message(String content, String senderID, String recipientID, String timeStamp) {
        this.content = content;
        this.senderID = senderID;
        this.recipientID = recipientID;
        this.timeStamp = timeStamp;
    }

    public void editMessage(String newMessage) {
        content = newMessage;
    }

    public String getContent()
    {
        return content;
    }
}
