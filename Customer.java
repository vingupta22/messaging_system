package messaging_system;

import java.util.*;
public class Customer /*extends user*/ {
    private ArrayList<String> productsPurchased;
    private ArrayList<Message> messageThread;

    public Customer() {
        this.productsPurchased = new ArrayList<>();
        this.messageThread = new ArrayList<>();
    }

    public ArrayList<String> getProductsPurchased() {
        return productsPurchased;
    }
}
