package ex_1_designpatterns.creational.factory;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=".repeat(50));
        System.out.println("🚗 WELCOME TO FACTORY PATTERN RENTAL SHOP 🚗");
        System.out.println("=".repeat(50));
        System.out.println("Learn how Factory Pattern works by renting vehicles!");
        System.out.println();
        
        while (true) {
            try {
                System.out.println("🏪 Available Vehicles:");
                System.out.println("1. 🚗 Car - Comfortable for long trips");
                System.out.println("2. 🏍️ Motorcycle - Fast and fuel efficient");
                System.out.println("3. 🚴 Bicycle - Eco-friendly and healthy");
                System.out.println("4. ❌ Exit");
                System.out.print("Choose your vehicle (1-4): ");
                
                int choice = scanner.nextInt();
                
                if (choice == 4) {
                    break;
                }
                
                String vehicleType;
                switch (choice) {
                    case 1: vehicleType = "car"; break;
                    case 2: vehicleType = "motorcycle"; break;
                    case 3: vehicleType = "bicycle"; break;
                    default:
                        System.out.println("❌ Invalid choice! Please try again.");
                        continue;
                }
                
                System.out.println();
                System.out.println("🏭 Factory Pattern in action...");
                System.out.println("   📞 Calling VehicleFactory.createVehicle(\"" + vehicleType + "\")");
                
                // Factory creates the vehicle (Factory Pattern demonstration)
                Vehicle vehicle = VehicleFactory.createVehicle(vehicleType);
                
                System.out.println("   ✅ Factory successfully created: " + vehicle.getClass().getSimpleName());
                System.out.println();
                
                // Display vehicle information
                System.out.println("==================================");
                System.out.println("📋 YOUR RENTAL VEHICLE DETAILS");
                System.out.println("==================================");
                vehicle.displayInfo();
                System.out.println();
                vehicle.displayFeatures();
                
                System.out.println();
                System.out.print("🕐 How many hours do you want to rent? ");
                int hours = scanner.nextInt();
                double totalCost = vehicle.getHourlyRate() * hours;
                
                System.out.println();
                System.out.println("==================================");
                System.out.println("🧾 RENTAL SUMMARY");
                System.out.println("==================================");
                System.out.println("🚗 Vehicle: " + vehicle.type);
                System.out.println("🕐 Duration: " + hours + " hours");
                System.out.printf("💰 Total Cost: $%.2f%n", totalCost);
                
                System.out.println();
                System.out.println("🚀 Starting your rental experience...");
                vehicle.start();
                
                System.out.println();
                System.out.print("Press Enter when you're done with your ride...");
                scanner.nextLine(); // consume newline
                scanner.nextLine(); // wait for user input
                
                System.out.println("🏁 Ending your rental...");
                vehicle.stop();
                
                System.out.println();
                System.out.println("🔍 Factory Pattern Explanation:");
                System.out.println("   • You chose a vehicle type (String input)");
                System.out.println("   • Factory method created the appropriate object");
                System.out.println("   • You didn't need to know the specific class constructors");
                System.out.println("   • Factory encapsulated the object creation logic!");
                
                System.out.println();
                System.out.print("🔄 Want to rent another vehicle? [y/n]: ");
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
        System.out.println("🙏 Thank you for learning Factory Pattern with us!");
        System.out.println("👨‍🏫 Key Takeaway: Factory Pattern hides object creation complexity!");
        scanner.close();
    }
}
