package ex_1_designpatterns.creational.factory;

import java.util.Scanner;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continueRenting = true;
        
        System.out.println("=".repeat(50));
        System.out.println("🚗 WELCOME TO FACTORY PATTERN RENTAL SHOP 🚗");
        System.out.println("=".repeat(50));
        System.out.println("Learn how Factory Pattern works by renting vehicles!");
        System.out.println();
        
        logger.info("Vehicle Factory application started");
        
        do {
            try {
                System.out.println("🏪 Available Vehicles:");
                System.out.println("1. 🚗 Car - Comfortable for long trips");
                System.out.println("2. 🏍️ Motorcycle - Fast and fuel efficient");
                System.out.println("3. 🚴 Bicycle - Eco-friendly and healthy");
                System.out.print("Choose your vehicle (1-3): ");
                
                int choice = getValidIntInput(scanner, 1, 3);
                String vehicleType = getVehicleTypeFromChoice(choice);
                
                System.out.println("\n🏭 Factory Pattern in action...");
                System.out.println("   📞 Calling VehicleFactory.createVehicle(\"" + vehicleType + "\")");
                
                Vehicle vehicle = VehicleFactory.createVehicle(vehicleType);
                System.out.println("   ✅ Factory successfully created: " + vehicle.getClass().getSimpleName());
                logger.info("Vehicle created: " + vehicle.getClass().getSimpleName());
                
                // Display vehicle information
                System.out.println("\n" + "=".repeat(40));
                System.out.println("📋 YOUR RENTAL VEHICLE DETAILS");
                System.out.println("=".repeat(40));
                vehicle.displayInfo();
                System.out.println();
                vehicle.displayFeatures();
                
                System.out.print("\n🕐 How many hours do you want to rent? ");
                int hours = getValidIntInput(scanner, 1, 24);
                double totalCost = vehicle.getHourlyRate() * hours;
                
                displayRentalSummary(vehicle, hours, totalCost);
                
                System.out.println("\n🚀 Starting your rental experience...");
                vehicle.start();
                
                System.out.print("\nPress Enter when you're done with your ride...");
                scanner.nextLine();
                
                System.out.println("🏁 Ending your rental...");
                vehicle.stop();
                
                displayPatternExplanation();
                
                continueRenting = getYesNoInput(scanner, "\n🔄 Want to rent another vehicle? [y/n]: ");
                
            } catch (Exception e) {
                logger.severe("Error during vehicle rental: " + e.getMessage());
                System.out.println("❌ An error occurred. Please try again.");
                continueRenting = getYesNoInput(scanner, "Continue with new rental? [y/n]: ");
            }
            
        } while (continueRenting);
        
        System.out.println("\n🙏 Thank you for learning Factory Pattern with us!");
        System.out.println("👨‍🏫 Key Takeaway: Factory Pattern hides object creation complexity!");
        logger.info("Vehicle Factory application ended");
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
        
        logger.warning("Max attempts exceeded, using default value");
        return min;
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
    
    private static String getVehicleTypeFromChoice(int choice) {
        switch (choice) {
            case 1: return "car";
            case 2: return "motorcycle";
            case 3: return "bicycle";
            default: return "car";
        }
    }
    
    private static void displayRentalSummary(Vehicle vehicle, int hours, double totalCost) {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("🧾 RENTAL SUMMARY");
        System.out.println("=".repeat(40));
        System.out.println("🚗 Vehicle: " + vehicle.getClass().getSimpleName());
        System.out.println("🕐 Duration: " + hours + " hours");
        System.out.printf("💰 Total Cost: $%.2f%n", totalCost);
    }
    
    private static void displayPatternExplanation() {
        System.out.println("\n🔍 Factory Pattern Explanation:");
        System.out.println("   • You chose a vehicle type (String input)");
        System.out.println("   • Factory method created the appropriate object");
        System.out.println("   • You didn't need to know the specific class constructors");
        System.out.println("   • Factory encapsulated the object creation logic!");
    }
}
