import java.util.ArrayList;

public class Seller extends Users {


    private ArrayList<Store> stores;
    private ArrayList<Message> messageThread;

    public Seller(String email, String password, ArrayList<Users> blockedUsers, ArrayList<Users> invisibleUsers,
                  ArrayList<Message> messagesSent, ArrayList<Message> messagesReceived,
                  ArrayList<Store> stores,
                  ArrayList<Message> messageThread) {
        super(email, password, blockedUsers, invisibleUsers, messagesSent, messagesReceived);
        this.stores = stores;
        this.messageThread = messageThread;
    }


    public ArrayList<Store> getStores() {
        return stores;
    }

    public void addStore(Store store) {
        stores.add(store);
    }

    public ArrayList<Message> getMessageThread() {
        return messageThread;
    }

    public void addMessage(Message message) {
        messageThread.add(message);
    }




}
