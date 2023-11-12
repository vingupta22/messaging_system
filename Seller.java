package messaging_system;

import java.util.ArrayList;

public class Seller extends Users {


    private ArrayList<Store> stores;
    private ArrayList<Message> messageThread;

    public Seller(String email, String password) {
        super(email, password);
        this.stores = new ArrayList<>();
        this.messageThread = new ArrayList<>();
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

    public void setStores(ArrayList<Store> storeList){
        this.stores = storeList;
    }


}
