package Services;

import Customer.Customer;
import Interfaces.Expirable;
import Interfaces.Shippable;
import Products.Product;
import Customer.Cart;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CheckoutService {

    private ShippingService shippingService;
    private static final double SHIPPING_FEE_PER_KG = 5.0; // Assumption: Shipping fee is 5 egp per kg.

    public CheckoutService(ShippingService shippingService) {
        this.shippingService = shippingService;
    }

    public void checkout(Customer customer, Cart cart) {
        if (cart.isEmpty()) {
            System.out.println("Cart is empty. Nothing to check out.");
            return;
        }

        for (Map.Entry<Product, Integer> entry : cart.getProducts().entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();

            // Check if there is enough stock
            if (product.getAvailableQuantity() < quantity) {
                System.out.println("ERROR: " + product.getName() + " is out of stock.");
                return;
            }

            // Check if the product is expirable and has expired
            if (product instanceof Expirable) {
                if (((Expirable) product).isExpired()) {
                    System.out.println("ERROR: " + product.getName() + " is expired.");
                    return;
                }
            }
        }

        double subtotal = 0;
        double totalWeight = 0;
        List<Shippable> shippableItems = new ArrayList<>();

        for (Map.Entry<Product, Integer> entry : cart.getProducts().entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();

            subtotal += product.getPrice() * quantity;

            // If a product is shippable, calculate its weight for the shipment
            if (product instanceof Shippable) {
                totalWeight += ((Shippable) product).getWeight() * quantity;
                shippableItems.add((Shippable) product);
            }
        }

        double shippingFee = totalWeight * SHIPPING_FEE_PER_KG;

        double totalAmount = subtotal + shippingFee;

        //Validate customer balance
        if (!customer.canAfford(totalAmount)) {
            System.out.println("ERROR: Insufficient balance. You need " + totalAmount + " but only have " + customer.getBalance() + ".");
            return;
        }

        customer.charge(totalAmount);
        for (Map.Entry<Product, Integer> entry : cart.getProducts().entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            product.decBulkQuantity(quantity); // Decrease stock
        }

        if (!shippableItems.isEmpty()) {
            // This assumes the ShippingService can use the cart map to get quantities
            shippingService.sendToShipping(shippableItems, cart.getProducts());
        }

        System.out.println("\n** Checkout receipt **");
        for (Map.Entry<Product, Integer> entry : cart.getProducts().entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            System.out.println(quantity + "x " + product.getName() + "\t" + (product.getPrice() * quantity));
        }
        System.out.println("------------------------");
        System.out.println("Subtotal\t" + subtotal);
        System.out.println("Shipping\t" + shippingFee);
        System.out.println("Amount\t\t" + totalAmount);
        System.out.println("\nCustomer balance after payment: " + customer.getBalance());

        // Clear the cart after successful checkout
        cart.removeAllProducts();
    }
}