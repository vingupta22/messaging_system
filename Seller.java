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

    public static void main(String[] args) {
        ArrayList<messaging_system.Message> messages = new ArrayList<messaging_system.Message>();
        messaging_system.Message m = new messaging_system.Message("Hey", null, null, null, false);
        messaging_system.Message l = new messaging_system.Message("Hi", null, null, null, false);
        messages.add(m);
        messages.add(l);
        ArrayList<String> prods = new ArrayList<String>();
        ArrayList<String> otherProds = new ArrayList<String>();
        prods.add("Gloves");
        prods.add("Briefcase");
        otherProds.add("Boots");
        otherProds.add("Jacket");
        Store s = new Store("S", prods, null);
        Store t = new Store("T", otherProds, null);

        ArrayList<Store> stores = new ArrayList<Store>();
        stores.add(s);
        stores.add(t);
        Seller theSeller = new Seller("a@google.com", "pancakes");
        for (var i = 0; i < stores.size(); i++) {
            stores.get(i).setSeller(theSeller);
        }
        theSeller.setStores(stores);



        if (theSeller.getStores().equals(stores)) {
            System.out.println("Test succeeded, stores successfully set and returned.");
        } else {
            System.out.println("Test failed, stores were not successfully set or returned.");
        }
        ArrayList<String> newProducts = new ArrayList<String>();
        newProducts.add("newShirt");
        newProducts.add("newShoes");
        Store newStore = new Store("New Store", newProducts, theSeller);
        theSeller.addStore(newStore);
        if (theSeller.getStores().get(2).equals(newStore)) {
            System.out.println("Test succeeded, store successfully added");
        } else {
            System.out.println("Test failed, store could not be added");
        }
        theSeller.addMessage(m);
        theSeller.addMessage(l);
        if (theSeller.messageThread.get(0).equals(m)) {
            System.out.println("Test succeeded, message added!");
        } else {
            System.out.println("Test failed, message could not be added!");

        }

        if (theSeller.getMessageThread().equals(messages)) {
            System.out.println("Test succeeded, message thread successfully got!");
        } else {
            System.out.println("Test failed, message thread not gotten");
        }






    }


}
