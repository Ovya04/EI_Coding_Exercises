package ex_1_designpatterns.creational.builder;

import java.util.Scanner;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continueOrdering = true;
        
        System.out.println("=".repeat(50));
        System.out.println("🍕 WELCOME TO BUILDER PATTERN PIZZA SHOP 🍕");
        System.out.println("=".repeat(50));
        System.out.println("Learn how Builder Pattern works by creating your custom pizza!");
        System.out.println();
        
        logger.info("Pizza Builder application started");
        
        do {
            try {
                // Size selection
                System.out.println("📏 Choose your pizza size:");
                System.out.println("1. Small ($8.99)");
                System.out.println("2. Medium ($12.99)");
                System.out.println("3. Large ($16.99)");
                System.out.print("Enter choice (1-3): ");
                
                int sizeChoice = getValidIntInput(scanner, 1, 3);
                String size = getSizeFromChoice(sizeChoice);
                
                // Create builder with chosen size
                Pizza.PizzaBuilder builder = new Pizza.PizzaBuilder(size);
                System.out.println("✅ " + size + " pizza base selected!");
                logger.info("Pizza size selected: " + size);
                
                // Toppings selection
                System.out.println("\n🧀 Choose your toppings (Builder Pattern in action!):");
                
                if (getYesNoInput(scanner, "Add Cheese? (+$1.50) [y/n]: ")) {
                    builder = builder.addCheese();
                    System.out.println("  ✅ Cheese added to your pizza!");
                    logger.info("Cheese topping added");
                }
                
                if (getYesNoInput(scanner, "Add Pepperoni? (+$2.00) [y/n]: ")) {
                    builder = builder.addPepperoni();
                    System.out.println("  ✅ Pepperoni added to your pizza!");
                    logger.info("Pepperoni topping added");
                }
                
                if (getYesNoInput(scanner, "Add Mushrooms? (+$1.25) [y/n]: ")) {
                    builder = builder.addMushrooms();
                    System.out.println("  ✅ Mushrooms added to your pizza!");
                    logger.info("Mushrooms topping added");
                }
                
                if (getYesNoInput(scanner, "Add Olives? (+$1.00) [y/n]: ")) {
                    builder = builder.addOlives();
                    System.out.println("  ✅ Olives added to your pizza!");
                    logger.info("Olives topping added");
                }
                
                // Build the final pizza
                System.out.println("\n🔨 Building your pizza using Builder Pattern...");
                Pizza pizza = builder.build();
                logger.info("Pizza built successfully: " + pizza.toString());
                
                // Display result
                System.out.println("\n" + "=".repeat(40));
                System.out.println("🎉 YOUR PIZZA IS READY!");
                System.out.println("=".repeat(40));
                System.out.println("📋 Order Details: " + pizza);
                System.out.printf("💰 Total Price: $%.2f%n", pizza.calculatePrice());
                displayPatternExplanation();
                
                continueOrdering = getYesNoInput(scanner, "\n🔄 Want to order another pizza? [y/n]: ");
                
            } catch (Exception e) {
                logger.severe("Error during pizza ordering: " + e.getMessage());
                System.out.println("❌ An error occurred. Please try again.");
                continueOrdering = getYesNoInput(scanner, "Continue with new order? [y/n]: ");
            }
            
        } while (continueOrdering);
        
        System.out.println("\n🙏 Thank you for learning Builder Pattern with us!");
        System.out.println("👨‍🏫 Key Takeaway: Builder Pattern allows step-by-step object construction!");
        logger.info("Pizza Builder application ended");
        scanner.close();
    }
    
    private static int getValidIntInput(Scanner scanner, int min, int max) {
        int attempts = 0;
        final int maxAttempts = 3;
        
        while (attempts < maxAttempts) {
            try {
                if (scanner.hasNextInt()) {
                    int input = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                    
                    if (input >= min && input <= max) {
                        return input;
                    } else {
                        System.out.print("❌ Please enter a number between " + min + " and " + max + ": ");
                    }
                } else {
                    System.out.print("❌ Please enter a valid number: ");
                    scanner.next(); // consume invalid input
                }
                attempts++;
            } catch (Exception e) {
                logger.warning("Invalid input during integer validation: " + e.getMessage());
                attempts++;
                System.out.print("❌ Please enter a valid number: ");
                scanner.nextLine();
            }
        }
        
        logger.warning("Max attempts exceeded, using default value");
        return min; // Default to minimum value
    }
    
    private static boolean getYesNoInput(Scanner scanner, String prompt) {
        int attempts = 0;
        final int maxAttempts = 3;
        
        while (attempts < maxAttempts) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim().toLowerCase();
                
                if (input.startsWith("y")) return true;
                if (input.startsWith("n")) return false;
                
                System.out.println("❌ Please enter 'y' for yes or 'n' for no.");
                attempts++;
            } catch (Exception e) {
                logger.warning("Error during yes/no input: " + e.getMessage());
                attempts++;
            }
        }
        
        logger.warning("Max attempts exceeded for yes/no input, defaulting to 'no'");
        return false;
    }
    
    private static String getSizeFromChoice(int choice) {
        switch (choice) {
            case 1: return "Small";
            case 2: return "Medium";
            case 3: return "Large";
            default: return "Medium";
        }
    }
    
    private static void displayPatternExplanation() {
        System.out.println("\n🔍 Builder Pattern Explanation:");
        System.out.println("   • Started with a base pizza size");
        System.out.println("   • Added toppings step by step using fluent interface");
        System.out.println("   • Built the final pizza object with build() method");
        System.out.println("   • Each step returns the builder for method chaining!");
    }
}
