package Customer;

public class Customer {
    private String name;
    private Double balance;

    public Customer(String name, Double balance) {
        this.name = name;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public Double getBalance() {
        return balance;
    }

    public boolean canAfford(double price) {
        return balance >= price;
    }

    public void charge(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount to charge cannot be negative.");
        }
        balance -= amount;
    }
    
}
