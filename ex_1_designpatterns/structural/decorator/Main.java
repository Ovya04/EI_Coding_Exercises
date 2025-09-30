package ex_1_designpatterns.structural.decorator;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=".repeat(50));
        System.out.println("☕ WELCOME TO DECORATOR PATTERN COFFEE SHOP ☕");
        System.out.println("=".repeat(50));
        System.out.println("Learn how Decorator Pattern adds features dynamically!");
        System.out.println();
        
        while (true) {
            try {
                // Start with basic coffee
                Coffee coffee = new BasicCoffee();
                System.out.println("🏁 Starting with: " + coffee.getDescription());
                System.out.printf("💰 Base price: $%.2f%n", coffee.getCost());
                System.out.println();
                System.out.println("🎨 Decorator Pattern in action - Add ingredients one by one!");
                
                boolean addingIngredients = true;
                int step = 1;
                
                while (addingIngredients) {
                    System.out.println();
                    System.out.println("🛒 STEP " + step + " - Choose an ingredient to add:");
                    System.out.println("1. 🥛 Milk (+$0.50)");
                    System.out.println("2. 🍯 Sugar (+$0.25)");
                    System.out.println("3. 🍦 Whipped Cream (+$0.75)");
                    System.out.println("4. 🌟 Vanilla Syrup (+$0.60)");
                    System.out.println("5. ✅ Finish my coffee");
                    System.out.print("Enter choice (1-5): ");
                    
                    int choice = scanner.nextInt();
                    
                    // Store reference to show decoration
                    
                    switch (choice) {
                        case 1:
                            coffee = new MilkDecorator(coffee);
                            System.out.println("   ✅ Added Milk! Wrapping previous coffee with MilkDecorator");
                            break;
                        case 2:
                            coffee = new SugarDecorator(coffee);
                            System.out.println("   ✅ Added Sugar! Wrapping previous coffee with SugarDecorator");
                            break;
                        case 3:
                            coffee = new WhipDecorator(coffee);
                            System.out.println("   ✅ Added Whipped Cream! Wrapping previous coffee with WhipDecorator");
                            break;
                        case 4:
                            coffee = new VanillaDecorator(coffee);
                            System.out.println("   ✅ Added Vanilla Syrup! Wrapping previous coffee with VanillaDecorator");
                            break;
                        case 5:
                            addingIngredients = false;
                            System.out.println("   ✅ Coffee customization complete!");
                            break;
                        default:
                            System.out.println("   ❌ Invalid choice! Please try again.");
                            continue;
                    }
                    
                    if (choice >= 1 && choice <= 4) {
                        System.out.println("   📝 Current order: " + coffee.getDescription());
                        System.out.printf("   💰 Current price: $%.2f%n", coffee.getCost());
                        step++;
                    }
                }
                
                // Final order summary
                System.out.println();
                System.out.println("==================================");
                System.out.println("☕ YOUR COFFEE IS READY!");
                System.out.println("==================================");
                System.out.println("📋 Final Order: " + coffee.getDescription());
                System.out.printf("💰 Total Price: $%.2f%n", coffee.getCost());
                
                // Show decorator pattern explanation
                System.out.println();
                System.out.println("🔍 Decorator Pattern Explanation:");
                System.out.println("   • Started with BasicCoffee object");
                System.out.println("   • Each ingredient wrapped the previous coffee object");
                System.out.println("   • Each decorator adds its own description and cost");
                System.out.println("   • Final object is a chain of decorators around basic coffee");
                System.out.println("   • You can add ingredients in any order and quantity!");
                
                // Show the decoration chain
                System.out.println();
                System.out.println("🔗 Object Wrapping Chain:");
                System.out.println("   Final Coffee Object");
                System.out.println("        ↓");
                System.out.println("   Last Decorator");
                System.out.println("        ↓");
                System.out.println("   ... (more decorators)");
                System.out.println("        ↓");
                System.out.println("   BasicCoffee");
                
                // Simulate coffee preparation
                System.out.println();
                System.out.println("👨‍🍳 Preparing your coffee...");
                System.out.println("☕ Brewing basic coffee...");
                Thread.sleep(1000);
                
                String[] parts = coffee.getDescription().split("\\+");
                for (int i = 1; i < parts.length; i++) {
                    System.out.println("🥄 Adding" + parts[i].trim() + "...");
                    Thread.sleep(500);
                }
                
                System.out.println("✨ Coffee ready for pickup!");
                
                System.out.println();
                System.out.print("🔄 Want to order another coffee? [y/n]: ");
                scanner.nextLine(); // consume newline
                if (!scanner.nextLine().toLowerCase().startsWith("y")) {
                    break;
                }
                System.out.println();
                
            } catch (Exception e) {
                System.out.println("❌ Invalid input or error occurred! Please try again.");
                scanner.nextLine(); // Clear invalid input
            }
        }
        
        System.out.println();
        System.out.println("🙏 Thank you for learning Decorator Pattern with us!");
        System.out.println("👨‍🏫 Key Takeaway: Decorator Pattern adds behavior without changing structure!");
        scanner.close();
    }
}
