package ar.com.instafood.models;

import java.util.List;

public class MenuResponse {

    private List<Product> mainProducts;
    private List<Product> drinkProducts;
    private List<Product> secondaryProducts;

    public List<Product> mainProducts() {
        return mainProducts;
    }
    public List<Product> drinkProducts() {
        return drinkProducts;
    }
    public List<Product> secondaryProducts() {
        return secondaryProducts;
    }
}
