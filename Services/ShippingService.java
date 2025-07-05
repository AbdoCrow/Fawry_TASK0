package Services;

import Interfaces.Shippable;
import Products.Product; // You need to import Product to work with the Map

import java.util.List;
import java.util.Map; // Import Map

public class ShippingService {

    public void sendToShipping(List<Shippable> itemsToShip, Map<Product, Integer> cartItems) {
        System.out.println("\n** Shipment notice **");
        double totalWeightInKg = 0;

        // Iterate through the list of items that need to be shipped
        for (Shippable item : itemsToShip) {
            // Find the quantity of the current item from the cart map
            int quantity = cartItems.get((Product) item);

            // Calculate the total weight for all quantities of this item
            totalWeightInKg += item.getWeight() * quantity;

            // Convert weight from kg to grams for display (e.g., 0.4kg -> 400g)
            int weightInGrams = (int) (item.getWeight() * 1000);

            // Print the correctly formatted line, like "2x Cheese 400g"
            System.out.println(quantity + "x " + item.getName() + "\t" + weightInGrams + "g");
        }

        // Print the final total weight in kilograms
        System.out.println("Total package weight " + totalWeightInKg + "kg");
    }
}