package Products;
public abstract class Product {
    private String name;
    private double price;
    private int availableQuantity;

    public Product(String name, double price, int availableQuantity) {
        this.name = name;
        this.price = price;
        this.availableQuantity = availableQuantity;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(int availableQuantity) { 
        this.availableQuantity = availableQuantity;
    }

    public void decQuantity() { //for Single purchases
        if (availableQuantity > 0) {
            availableQuantity--;
        } else {
            throw new IllegalStateException("No available quantity to decrement.");
        }
    }

    public void decBulkQuantity(int quantity) { //for Bulk purchases
        if (availableQuantity >= quantity) {
            availableQuantity -= quantity;
        } else {
            throw new IllegalStateException("No available quantity to decrement.");
        }
    }

    public boolean isInStock() {
        return availableQuantity > 0;
    }
};
    