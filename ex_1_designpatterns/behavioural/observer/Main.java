package ex_1_designpatterns.behavioural.observer;

import java.util.Scanner;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        
        System.out.println("=".repeat(60));
        System.out.println("🌤️ WELCOME TO OBSERVER PATTERN WEATHER STATION 🌤️");
        System.out.println("=".repeat(60));
        System.out.println("Learn how Observer Pattern notifies multiple objects automatically!");
        System.out.println();
        
        // Create weather station
        System.out.print("🌍 Enter weather station location: ");
        String location = scanner.nextLine().trim();
        if (location.isEmpty()) {
            location = "Downtown";
        }
        
        WeatherStation weatherStation = new WeatherStation(location);
        System.out.println("✅ Weather Station created at " + location);
        System.out.println();
        
        while (true) {
            try {
                System.out.println("🎛️ OBSERVER PATTERN CONTROL PANEL");
                System.out.println("-".repeat(35));
                System.out.println("1. 📱 Add Phone Display");
                System.out.println("2. 🌐 Add Web Display");
                System.out.println("3. 📺 Add TV Display");
                System.out.println("4. ❌ Remove a Display");
                System.out.println("5. 🌡️ Update Weather Data");
                System.out.println("6. 🎲 Generate Random Weather");
                System.out.println("7. 📊 Show Observer Status");
                System.out.println("8. 🚪 Exit");
                System.out.print("Enter choice (1-8): ");
                
                int choice = scanner.nextInt();
                scanner.nextLine(); // consume newline
                
                switch (choice) {
                    case 1: // Add Phone Display
                        System.out.print("📱 Enter phone name (e.g., iPhone, Samsung): ");
                        String phoneName = scanner.nextLine().trim();
                        if (phoneName.isEmpty()) phoneName = "Mobile Device";
                        
                        PhoneDisplay phoneDisplay = new PhoneDisplay(phoneName);
                        weatherStation.registerObserver(phoneDisplay);
                        System.out.println("📱 " + phoneName + " is now observing weather changes!");
                        break;
                        
                    case 2: // Add Web Display
                        WebDisplay webDisplay = new WebDisplay();
                        weatherStation.registerObserver(webDisplay);
                        System.out.println("🌐 Web Dashboard is now observing weather changes!");
                        break;
                        
                    case 3: // Add TV Display
                        System.out.print("📺 Enter TV brand (e.g., Samsung, LG, Sony): ");
                        String tvBrand = scanner.nextLine().trim();
                        if (tvBrand.isEmpty()) tvBrand = "Smart TV";
                        
                        TVDisplay tvDisplay = new TVDisplay(tvBrand);
                        weatherStation.registerObserver(tvDisplay);
                        System.out.println("📺 " + tvBrand + " Smart TV is now observing weather changes!");
                        break;
                        
                    case 4: // Remove Display
                        if (weatherStation.getObserverCount() == 0) {
                            System.out.println("❌ No displays to remove!");
                        } else {
                            System.out.println("🗑️ Note: In this demo, removal requires code modification");
                            System.out.println("   In real implementation, you'd select from active observers");
                        }
                        break;
                        
                    case 5: // Update Weather Data
                        System.out.print("🌡️ Enter temperature (°C): ");
                        float temp = scanner.nextFloat();
                        System.out.print("💧 Enter humidity (%): ");
                        float humidity = scanner.nextFloat();
                        System.out.print("📊 Enter pressure (hPa): ");
                        float pressure = scanner.nextFloat();
                        scanner.nextLine(); // consume newline
                        
                        System.out.println();
                        System.out.println("🔄 OBSERVER PATTERN IN ACTION!");
                        weatherStation.setWeatherData(temp, humidity, pressure);
                        break;
                        
                    case 6: // Generate Random Weather
                        float randomTemp = 15 + random.nextFloat() * 20; // 15-35°C
                        float randomHumidity = 30 + random.nextFloat() * 50; // 30-80%
                        float randomPressure = 980 + random.nextFloat() * 60; // 980-1040 hPa
                        
                        System.out.println("🎲 Generating random weather data...");
                        System.out.println("🔄 OBSERVER PATTERN IN ACTION!");
                        weatherStation.setWeatherData(randomTemp, randomHumidity, randomPressure);
                        break;
                        
                    case 7: // Show Observer Status
                        System.out.println();
                        System.out.println("📊 OBSERVER PATTERN STATUS");
                        System.out.println("============================");
                        System.out.println("🌍 Weather Station: " + weatherStation.getLocation());
                        System.out.println("👥 Active Observers: " + weatherStation.getObserverCount());
                        System.out.println();
                        System.out.println("🔍 Pattern Explanation:");
                        System.out.println("   • WeatherStation = Subject (Observable)");
                        System.out.println("   • Displays = Observers (Subscribers)");
                        System.out.println("   • When weather changes, ALL observers get notified");
                        System.out.println("   • Each observer updates its display automatically");
                        System.out.println("   • Loose coupling: Station doesn't know observer details");
                        break;
                        
                    case 8: // Exit
                        System.out.println("🌤️ Weather monitoring session ended!");
                        System.out.println();
                        System.out.println("🙏 Thank you for learning Observer Pattern!");
                        System.out.println("👨‍🏫 Key Takeaway: Observer Pattern enables automatic notifications!");
                        System.out.println("🎯 Benefits: Loose coupling, dynamic subscription, broadcast communication!");
                        return;
                        
                    default:
                        System.out.println("❌ Invalid choice! Please try again.");
                        break;
                }
                
                System.out.println();
                System.out.print("Press Enter to continue...");
                scanner.nextLine();
                System.out.println();
                
            } catch (Exception e) {
                System.out.println("❌ Invalid input! Please try again.");
                scanner.nextLine(); // Clear invalid input
            }
     
        }        
       
    }
}
