package ex_1_designpatterns.structural.decorator;

import java.util.Scanner;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continueOrdering = true;
        
        System.out.println("=".repeat(50));
        System.out.println("☕ WELCOME TO DECORATOR PATTERN COFFEE SHOP ☕");
        System.out.println("=".repeat(50));
        System.out.println("Learn how Decorator Pattern adds features dynamically!");
        System.out.println();
        
        logger.info("Decorator Pattern Coffee Shop started");
        
        do {
            try {
                // Start with basic coffee
                Coffee coffee = new BasicCoffee();
                System.out.println("🏁 Starting with: " + coffee.getDescription());
                System.out.printf("💰 Base price: $%.2f%n", coffee.getCost());
                System.out.println("\n🎨 Decorator Pattern in action - Add ingredients one by one!");
                
                boolean addingIngredients = true;
                int step = 1;
                
                while (addingIngredients) {
                    System.out.println("\n🛒 STEP " + step + " - Choose an ingredient to add:");
                    System.out.println("1. 🥛 Milk (+$0.50)");
                    System.out.println("2. 🍯 Sugar (+$0.25)");
                    System.out.println("3. 🍦 Whipped Cream (+$0.75)");
                    System.out.println("4. 🌟 Vanilla Syrup (+$0.60)");
                    System.out.println("5. ✅ Finish my coffee");
                    System.out.print("Enter choice (1-5): ");
                    
                    int choice = getValidIntInput(scanner, 1, 5);
                    
                    if (choice == 5) {
                        addingIngredients = false;
                        System.out.println("   ✅ Coffee customization complete!");
                        logger.info("Coffee customization completed");
                        continue;
                    }
                    
                    coffee = addIngredient(coffee, choice);
                    if (coffee != null) {
                        System.out.println("   📝 Current order: " + coffee.getDescription());
                        System.out.printf("   💰 Current price: $%.2f%n", coffee.getCost());
                        logger.info("Ingredient added, current coffee: " + coffee.getDescription());
                        step++;
                    }
                }
                
                // Final order summary
                displayOrderSummary(coffee);
                displayPatternExplanation();
                
                // Simulate coffee preparation
                simulatePreparation(coffee);
                
                continueOrdering = getYesNoInput(scanner, "\n🔄 Want to order another coffee? [y/n]: ");
                
            } catch (Exception e) {
                logger.severe("Error during coffee ordering: " + e.getMessage());
                System.out.println("❌ An error occurred. Please try again.");
                continueOrdering = getYesNoInput(scanner, "Continue with new order? [y/n]: ");
            }
            
        } while (continueOrdering);
        
        System.out.println("\n🙏 Thank you for learning Decorator Pattern with us!");
        System.out.println("👨‍🏫 Key Takeaway: Decorator Pattern adds behavior without changing structure!");
        logger.info("Decorator Pattern Coffee Shop ended");
        scanner.close();
    }
    
    private static int getValidIntInput(Scanner scanner, int min, int max) {
        int attempts = 0;
        final int maxAttempts = 3;
        
        while (attempts < maxAttempts) {
            try {
                if (scanner.hasNextInt()) {
                    int input = scanner.nextInt();
                    scanner.nextLine();
                    
                    if (input >= min && input <= max) {
                        return input;
                    } else {
                        System.out.print("❌ Please enter a number between " + min + " and " + max + ": ");
                    }
                } else {
                    System.out.print("❌ Please enter a valid number: ");
                    scanner.next();
                }
                attempts++;
            } catch (Exception e) {
                logger.warning("Invalid input: " + e.getMessage());
                attempts++;
                scanner.nextLine();
            }
        }
        
        logger.warning("Max attempts exceeded, using finish option");
        return max; // Default to finish option
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
                logger.warning("Error during input: " + e.getMessage());
                attempts++;
            }
        }
        
        return false;
    }
    
    private static Coffee addIngredient(Coffee coffee, int choice) {
        switch (choice) {
            case 1:
                System.out.println("   ✅ Added Milk! Wrapping previous coffee with MilkDecorator");
                return new MilkDecorator(coffee);
            case 2:
                System.out.println("   ✅ Added Sugar! Wrapping previous coffee with SugarDecorator");
                return new SugarDecorator(coffee);
            case 3:
                System.out.println("   ✅ Added Whipped Cream! Wrapping previous coffee with WhipDecorator");
                return new WhipDecorator(coffee);
            case 4:
                System.out.println("   ✅ Added Vanilla Syrup! Wrapping previous coffee with VanillaDecorator");
                return new VanillaDecorator(coffee);
            default:
                System.out.println("   ❌ Invalid choice!");
                return coffee;
        }
    }
    
    private static void displayOrderSummary(Coffee coffee) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("☕ YOUR COFFEE IS READY!");
        System.out.println("=".repeat(50));
        System.out.println("📋 Final Order: " + coffee.getDescription());
        System.out.printf("💰 Total Price: $%.2f%n", coffee.getCost());
    }
    
    private static void displayPatternExplanation() {
        System.out.println("\n🔍 Decorator Pattern Explanation:");
        System.out.println("   • Started with BasicCoffee object");
        System.out.println("   • Each ingredient wrapped the previous coffee object");
        System.out.println("   • Each decorator adds its own description and cost");
        System.out.println("   • Final object is a chain of decorators around basic coffee");
    }
    
    private static void simulatePreparation(Coffee coffee) {
        try {
            System.out.println("\n👨‍🍳 Preparing your coffee...");
            System.out.println("☕ Brewing basic coffee...");
            Thread.sleep(1000);
            
            String[] parts = coffee.getDescription().split("\\+");
            for (int i = 1; i < parts.length; i++) {
                System.out.println("🥄 Adding" + parts[i].trim() + "...");
                Thread.sleep(500);
            }
            
            System.out.println("✨ Coffee ready for pickup!");
        } catch (InterruptedException e) {
            logger.warning("Sleep interrupted during coffee preparation");
        }
    }
}
