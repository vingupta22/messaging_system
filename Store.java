package messaging_system;

import java.util.ArrayList;

/**
 * Project 5 Option 2
 * <p>
 * This program holds a class called Store that models a type of store in an e-commerce system that is 
 * created by sellers. It allows seller to modify store by adding products and changing store names 
 * when extends this class.
 *
 * @author Ishaan, Nandini, Nick, Vinay, Zishou, LO1
 * @version December 10, 2023
 */

public class Store {

    private String name; // creating name field
    ArrayList<String> productList; // creating product list field
    private Seller seller; // creating seller field


    public Store(String name, ArrayList<String> productList, Seller seller) {
        this.name = name;
        this.productList = productList;
        this.seller = seller;
    } //Store constructor that sets the naem and product of a store and who owns the store

    public String getName() {
        return name;
    } // return the name of the store

    public void setName(String name) {
        this.name = name;
    } // change the name of the store

    public ArrayList<String> getProductList() {
        return productList;
    } // get the products that are in the store


    public Seller getSeller() {
        return seller;
    } // get the seller of the store

    public void setSeller(Seller s) {
        this.seller = s;
    } // change the seller of the store

    public boolean itemExists(String item) {
        for (String product : productList) {
            if (item.equalsIgnoreCase(product)) {
                return true;
            }
        }
        return false;
    } // check if a product are still available in the store

    //Main method for testing
    public static void main(String[] args) {
        ArrayList<String> products = new ArrayList<>();
        products.add("Iphone 91");
        products.add("Ipod 24");
        Seller customers = new Seller("apple@apple.com", "andriod");
        Store apple = new Store("Apple", products, customers);

        System.out.println("Test 1 for getName method");
        if (apple.getName().equals("Apple")) {
            System.out.println("getName method passed the test");
        } else {
            System.out.println("There's an error in the getName method");
        }
        System.out.println("Test 2 for getEmail method");
        if (apple.getSeller().getEmail().equals("apple@apple.com")) {
            System.out.println("getEmail method passed the test");
        } else {
            System.out.println("There's an error in the getEmail method");
        }
        System.out.println("Test 3 for getPassword method");
        if (apple.getSeller().getPassword().equals("andriod")) {
            System.out.println("getPassword method passed the test");
        } else {
            System.out.println("There's an error in the getPassword method");
        }
        System.out.println("Test 4 for getProductList method");
        if (apple.getProductList().equals(products)) {
            System.out.println("getProductList method passed the test");

        } else {
            System.out.println("There's an error in the getProductList method");
        }

        System.out.println("Test 5 for setName method");
        apple.setName("Andriod");
        if (apple.getName().equals("Andriod")) {
            System.out.println("setName method passed the test");
        } else {
            System.out.println("There's an error in the setName method");
        }
        System.out.println("Test 6 for itemExists method");
        if (apple.itemExists("Iphone 91")) {
            System.out.println("itemExists method passed the test");
        } else {
            System.out.println("There's an error in the itemExists method");
        }

    }

}
