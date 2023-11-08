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

    public boolean itemExists(String item){
        for(String product: productList){
            if(item.equalsIgnoreCase(product)){
                return true;
            }
        }
        return false;
    }


}
