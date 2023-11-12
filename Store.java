package messaging_system;

import java.util.ArrayList;

public class Store {


    private String name;
    ArrayList<String> productList;
    private Seller seller;


    public Store(String name, ArrayList<String> productList, Seller seller) {
        this.name = name;
        this.productList = productList;
        this.seller = seller;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getProductList() {
        return productList;
    }


    public Seller getSeller() {
        return seller;
    }

    public boolean itemExists(String item) {
        for (String product : productList) {
            if (item.equalsIgnoreCase(product)) {
                return true;
            }
        }
        return false;
    }

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
