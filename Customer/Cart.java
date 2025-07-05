package Customer;

import java.util.Map;
import Products.*;

public class Cart {
    Map<Product, Integer> products;

    public Cart(Map<Product, Integer> products) {
        this.products = products;
    }

    public Map<Product, Integer> getProducts() {
        return products;
    }

    public void addProduct(Product product, int quantity) {
        if (products.containsKey(product)) {
            products.put(product, products.get(product) + quantity);
        } else {
            products.put(product, quantity);
        }
    }

    public boolean isEmpty() {
        return products.isEmpty();
    }

    public void removeProduct(Product product) {
        if (products.containsKey(product)) {
            products.remove(product);
        }
    }

    public void removeAllProducts() {
        products.clear();
    }
}
