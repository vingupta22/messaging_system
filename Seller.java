package messaging_system;

import java.util.ArrayList;

/**
 * Project 5 Option 2
 *
 * This program holds a class called Seller that models a type of seller in an e-commerce system (extends user).
 * It's responsible for functions relating to creating and adding stores, and messages that sellers receives
 * This class will later be used in the server/client (processor/client) classes when user choose their user type to be
 * seller
 *
 * @author Ishaan, Nandini, Nick, Vinay, Zishuo, LO1
 *
 * @version December 10, 2023
 *
 */

public class Seller extends Users {


    private ArrayList<Store> stores; // creating store list field
    private final ArrayList<Message> messageThread; // creating messageThread list field

    public Seller(String email, String password) {
        super(email, password);
        this.stores = new ArrayList<>();
        this.messageThread = new ArrayList<>();
    } // Seller constructor, that allows seller to get set their email,password by using methods in user,
    // Also creates a blank store and messageThread list


    public ArrayList<Store> getStores() {
        return stores;
    } // returns the seller's store

    public void addStore(Store store) {
        stores.add(store);
    } // adds a store in the seller's store list

    public ArrayList<Message> getMessageThread() {
        return messageThread;
    } // return the seller's messages

    public void addMessage(Message message) {
        messageThread.add(message);
    } // adds a message in the seller's messageThread list

    public void setStores(ArrayList<Store> storeList) {
        this.stores = storeList;
    } // allows the seller to adjust the store list

    //main method for testing
    public static void main(String[] args) {
        ArrayList<messaging_system.Message> messages = new ArrayList<messaging_system.Message>();
        messaging_system.Message m = new messaging_system.Message("Hey", null, null,
                null, false);
        messaging_system.Message l = new messaging_system.Message("Hi", null, null,
                null, false);
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
