package messaging_system;

import java.util.*;

/**
 * Project 5 Option 2
 *
 * This program holds a class called Customer that models a type of customer in an e-commerce system (extends user).
 * Includes functions such as being able to buy products, specifically for users that defined themselves as
 * customers
 *
 * @author Ishaan, Nandini, Nick, Vinay, Zishuo, LO1
 *
 * @version December 10, 2023
 *
 */

public class Customer extends Users {
    private ArrayList<String> productsPurchased; // create productPurchase list field

    public Customer(String email, String password) {
        super(email, password);
        this.productsPurchased = new ArrayList<>();
    } // constructor for customers that allows them to initialize email, password, and a new list of products
      // purchased

    public ArrayList<String> getProductsPurchased() {
        return productsPurchased;
    } // return the products purchased by customers

    public void setProductsPurchased(ArrayList<String> products) {
        this.productsPurchased = products;
    } // change products purchases list that a customer has bought

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
