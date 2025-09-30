package ex_1_designpatterns.creational.builder;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=".repeat(50));
        System.out.println("🍕 WELCOME TO BUILDER PATTERN PIZZA SHOP 🍕");
        System.out.println("=".repeat(50));
        System.out.println("Learn how Builder Pattern works by creating your custom pizza!");
        System.out.println();
        
        while (true) {
            try {
                // Size selection
                System.out.println("📏 Choose your pizza size:");
                System.out.println("1. Small ($8.99)");
                System.out.println("2. Medium ($12.99)");
                System.out.println("3. Large ($16.99)");
                System.out.print("Enter choice (1-3): ");
                
                int sizeChoice = scanner.nextInt();
                String size;
                switch (sizeChoice) {
                    case 1: size = "Small"; break;
                    case 2: size = "Medium"; break;
                    case 3: size = "Large"; break;
                    default: 
                        System.out.println("❌ Invalid choice! Using Medium as default.");
                        size = "Medium"; 
                        break;
                }
                
                // Create builder with chosen size
                Pizza.PizzaBuilder builder = new Pizza.PizzaBuilder(size);
                System.out.println("✅ " + size + " pizza base selected!");
                System.out.println();
                
                // Toppings selection
                System.out.println("🧀 Choose your toppings (Builder Pattern in action!):");
                
                System.out.print("Add Cheese? (+$1.50) [y/n]: ");
                if (scanner.next().toLowerCase().startsWith("y")) {
                    builder = builder.addCheese();
                    System.out.println("  ✅ Cheese added to your pizza!");
                }
                
                System.out.print("Add Pepperoni? (+$2.00) [y/n]: ");
                if (scanner.next().toLowerCase().startsWith("y")) {
                    builder = builder.addPepperoni();
                    System.out.println("  ✅ Pepperoni added to your pizza!");
                }
                
                System.out.print("Add Mushrooms? (+$1.25) [y/n]: ");
                if (scanner.next().toLowerCase().startsWith("y")) {
                    builder = builder.addMushrooms();
                    System.out.println("  ✅ Mushrooms added to your pizza!");
                }
                
                System.out.print("Add Olives? (+$1.00) [y/n]: ");
                if (scanner.next().toLowerCase().startsWith("y")) {
                    builder = builder.addOlives();
                    System.out.println("  ✅ Olives added to your pizza!");
                }
                
                // Build the final pizza
                System.out.println();
                System.out.println("🔨 Building your pizza using Builder Pattern...");
                Pizza pizza = builder.build();
                
                // Display result
                System.out.println();
                System.out.println("======================================================");
                System.out.println("🎉 YOUR PIZZA IS READY!");
                System.out.println("=======================================================");
                System.out.println("📋 Order Details: " + pizza);
                System.out.printf("💰 Total Price: $%.2f%n", pizza.calculatePrice());
                System.out.println();
                System.out.println("🔍 Builder Pattern Explanation:");
                System.out.println("   • Started with a base pizza size");
                System.out.println("   • Added toppings step by step using fluent interface");
                System.out.println("   • Built the final pizza object with build() method");
                System.out.println("   • Each step returns the builder for method chaining!");
                
                System.out.println();
                System.out.print("🔄 Want to order another pizza? [y/n]: ");
                if (!scanner.next().toLowerCase().startsWith("y")) {
                    break;
                }
                System.out.println();
                
            } catch (Exception e) {
                System.out.println("❌ Invalid input! Please try again.");
                scanner.nextLine(); // Clear invalid input
            }
        }
        
        System.out.println();
        System.out.println("🙏 Thank you for learning Builder Pattern with us!");
        System.out.println("👨‍🏫 Key Takeaway: Builder Pattern allows step-by-step object construction!");
        scanner.close();
    }
}
