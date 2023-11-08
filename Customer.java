package messaging_system;

import java.util.*;
public class Customer extends Users {
    private ArrayList<String> productsPurchased;
    private ArrayList<Message> messageThread;

    public Customer(String email, String password) {
        super(email, password);
        this.productsPurchased = new ArrayList<>();
        this.messageThread = new ArrayList<>();
    }

    public ArrayList<String> getProductsPurchased() {
        return productsPurchased;
    }
}
