package Products;

import Interfaces.Shippable;

public class Mobile extends Product implements Shippable {
    private double weight;

    public Mobile(String name, double price, int availableQuantity, double weight) {
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

    
}
