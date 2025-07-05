package Products;

import Interfaces.Shippable;

public class TV extends Product implements Shippable {
    private double weight;

    public TV(String name, double price, int availableQuantity, double weight) {
        super(name, price, availableQuantity);
        this.weight = weight;
    }

    @Override
    public double getWeight() {
        return weight;
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public boolean isInStock() {
        return super.isInStock();
    }
    
}
