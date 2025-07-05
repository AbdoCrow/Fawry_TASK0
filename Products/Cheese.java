package Products;

import Interfaces.Shippable;
import Interfaces.Expirable;
import java.time.LocalDate;

public class Cheese extends Product implements Expirable, Shippable {
    private double weight;
    private LocalDate expirationDate;

    public Cheese(String name, double price, int availableQuantity, double weight, LocalDate expirationDate) {
        super(name, price, availableQuantity);
        this.weight = weight;
        this.expirationDate = expirationDate;
    }

    @Override
    public double getWeight() {
        return weight;
    }

    @Override
    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    @Override
    public boolean isExpired() {
        if (expirationDate == null) {
            throw new IllegalStateException("Expiration date is not set.");
        }
        return expirationDate.isBefore(LocalDate.now());
    }
}
