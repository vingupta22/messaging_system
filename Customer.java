package messaging_system;

import java.util.*;

/**
 * Project 4 Option 2
 *
 * This program holds a class called Customer that models a type of customer in an e-commerce system (extends user).
 *
 * @author Ishaan, Nandini, Nick, Vinay, Zishou, LO1
 *
 * @version November 13, 2023
 *
 */

public class Customer extends Users {
    private ArrayList<String> productsPurchased;

    public Customer(String email, String password) {
        super(email, password);
        this.productsPurchased = new ArrayList<>();
    }

    public ArrayList<String> getProductsPurchased() {
        return productsPurchased;
    }

    public void setProductsPurchased(ArrayList<String> products) {
        this.productsPurchased = products;
    }

    //main method for testing
    public static void main(String[] args) {

        Users user = new Users("user@example.com", "password");

        System.out.println("Test 2: getEmail and getPassword methods");
        if (user.getEmail().equals("user@example.com") && user.getPassword().equals("password")) {
            System.out.println("Success: getEmail and getPassword methods");
        } else {
            System.out.println("Test failed: Incorrect email or password");
        }

        System.out.println("Test 3: hide and getInvisibleUsers methods");
        user.hide("hiddenUser");
        ArrayList<String> invisibleUsers = user.getInvisibleUsers();
        if (invisibleUsers.size() == 1 && invisibleUsers.contains("hiddenUser")) {
            System.out.println("Success: hide and getInvisibleUsers methods");
        } else {
            System.out.println("Test failed: Invisible users not updated correctly");
        }

        System.out.println("Test 4: block and getBlockedUsers methods");
        user.block("blockedUser");
        ArrayList<String> blockedUsers = user.getBlockedUsers();
        if (blockedUsers.size() == 1 && blockedUsers.contains("blockedUser")) {
            System.out.println("Success: block and getBlockedUsers methods");
        } else {
            System.out.println("Test failed: Blocked users not updated correctly");
        }

        System.out.println("Test 5: deleteAccnt method");
        user.deleteAccnt();
        if (user.getEmail() == null) {
            System.out.println("Success: deleteAccnt method");
        } else {
            System.out.println("Test failed: Email not deleted after deleting account");
        }

        System.out.println("Test 6: editAccnt method");
        user.editAccnt("newemail@example.com", "newpassword");
        if (user.getEmail().equals("newemail@example.com") && user.getPassword().equals("newpassword")) {
            System.out.println("Success: editAccnt method");
        } else {
            System.out.println("Test failed: Email or password not updated correctly");
        }

        System.out.println("All tests completed!");
    }
}
