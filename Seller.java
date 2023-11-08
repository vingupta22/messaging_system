import java.util.ArrayList;

public class Seller  /*extends User*/ {


    private ArrayList<Store> stores;
    private ArrayList<Message> messageThread;

    public Seller(ArrayList<Store> stores, ArrayList<Message> messageThread) {
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
