import Customer.*;
import Interfaces.Expirable;
import Products.*;
import Services.CheckoutService;
import Services.ShippingService;
import java.time.LocalDate;
import java.util.*;
import java.util.Map;

public class Demo {
    public static void main(String[] args) {
        //The inventory
        List<Product> inventory = new ArrayList<>();
        inventory.add(new Cheese("Cheese", 100, 10, 0.2, LocalDate.now().plusMonths(6)));
        inventory.add(new Biscuits("Biscuits", 150, 20, 0.7, LocalDate.now().plusYears(1)));
        inventory.add(new TV("TV", 5000, 5, 15.0));
        inventory.add(new ScratchCard("Scratch Card", 20, 100));
        inventory.add(new Mobile("Mobile", 2500, 8, 0.3));
        inventory.add(new Cheese("Expired Cheese", 50, 5, 0.2, LocalDate.now().minusDays(1)));

        //Adding the Dummy customer
        Customer customer = new Customer("Abdalrahman", 10000.0);
        ShippingService shippingService = new ShippingService();
        CheckoutService checkoutService = new CheckoutService(shippingService);
        Map<Product, Integer> products = new HashMap<>();
        Cart cart = new Cart(products);
        Scanner scanner = new Scanner(System.in);

        //INTERACTIVE MENU LOOP
        boolean running = true;
        while (running) {
            System.out.println("\n===== E-COMMERCE MENU =====");
            System.out.println("Customer: " + customer.getName() + " | Balance: " + String.format("%.2f", customer.getBalance()));
            System.out.println("\n--- Available Products ---");
            for (int i = 0; i < inventory.size(); i++) {
                Product p = inventory.get(i);
                System.out.printf("ID: %d | %-15s | Price: %-8.2f | Stock: %d\n", i, p.getName(), p.getPrice(), p.getAvailableQuantity());
            }
            System.out.println("1. Add Product to Cart");
            System.out.println("2. View Cart");
            System.out.println("3. Checkout");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            String choiceInput = scanner.nextLine();
            int choice;
            // Validate that the input is a number
            if (!choiceInput.matches("\\d+")) { //had to search for this regex (didn't know about it)
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }
            choice = Integer.parseInt(choiceInput);

            switch (choice) {
                case 1: // Add Product to Cart
                    System.out.print("Enter Product ID to add: ");
                        int productId = Integer.parseInt(scanner.nextLine());
                        if (productId < 0 || productId >= inventory.size()) {
                            System.out.println("Invalid Product ID.");
                            break;
                        }
                        Product selectedProduct = inventory.get(productId);

                        System.out.print("Enter quantity: ");
                        int quantity = Integer.parseInt(scanner.nextLine());
                        if (quantity <= 0) {
                            System.out.println("Quantity must be positive.");
                            break;
                        }
                        if(selectedProduct.getAvailableQuantity() < quantity){
                            System.out.println("Not enough stock available.");
                            break;
                        }

                        if (selectedProduct instanceof Expirable) {
                            if (((Expirable) selectedProduct).isExpired()) {
                                System.out.println("Cannot add expired item to cart: " + selectedProduct.getName());
                                break;
                            }
                        }
                        cart.addProduct(selectedProduct, quantity);
                        System.out.println(quantity + "x " + selectedProduct.getName() + " added to cart.");

                    break;

                case 2: // View Cart
                    System.out.println("\n--- Your Shopping Cart ---");
                    if (cart.isEmpty()) {
                        System.out.println("Your cart is empty.");
                    } else {
                        cart.getProducts().forEach((product, qty) ->
                                System.out.printf("%dx %-15s | Price: %.2f each\n", qty, product.getName(), product.getPrice())
                        );
                    }
                    break;

                case 3: // Checkout

                    checkoutService.checkout(customer, cart);
                    break;

                case 4: // Exit
                    running = false;
                    System.out.println("Thank you for shopping. Exiting...");
                    break;

                default:
                    System.out.println("Invalid option. Please choose between 1 and 4.");
                    break;
            }
        }
        scanner.close();
    }
}
