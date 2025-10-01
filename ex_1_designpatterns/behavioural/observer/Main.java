package ex_1_designpatterns.behavioural.observer;

import java.util.Scanner;
import java.util.Random;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        boolean continueOperating = true;
        
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
        
        logger.info("Observer Pattern Weather Station started at location: " + location);
        
        do {
            try {
                System.out.println("🎛️ OBSERVER PATTERN CONTROL PANEL");
                System.out.println("-".repeat(35));
                System.out.println("1. 📱 Add Phone Display");
                System.out.println("2. 🌐 Add Web Display");
                System.out.println("3. 📺 Add TV Display");
                System.out.println("4. 🌡️ Update Weather Data");
                System.out.println("5. 🎲 Generate Random Weather");
                System.out.println("6. 📊 Show Observer Status");
                System.out.println("7. 🚪 Exit");
                System.out.print("Enter choice (1-7): ");
                
                int choice = getValidIntInput(scanner, 1, 7);
                
                switch (choice) {
                    case 1: // Add Phone Display
                        addPhoneDisplay(scanner, weatherStation);
                        break;
                        
                    case 2: // Add Web Display
                        WebDisplay webDisplay = new WebDisplay();
                        weatherStation.registerObserver(webDisplay);
                        System.out.println("🌐 Web Dashboard is now observing weather changes!");
                        logger.info("Web Display added as observer");
                        break;
                        
                    case 3: // Add TV Display
                        addTVDisplay(scanner, weatherStation);
                        break;
                        
                    case 4: // Update Weather Data
                        updateWeatherManually(scanner, weatherStation);
                        break;
                        
                    case 5: // Generate Random Weather
                        generateRandomWeather(random, weatherStation);
                        break;
                        
                    case 6: // Show Observer Status
                        showObserverStatus(weatherStation);
                        break;
                        
                    case 7: // Exit
                        System.out.println("🌤️ Weather monitoring session ended!");
                        continueOperating = false;
                        break;
                        
                    default:
                        System.out.println("❌ Invalid choice! Please try again.");
                        break;
                }
                
                if (continueOperating && choice != 6) {
                    System.out.print("\nPress Enter to continue...");
                    scanner.nextLine();
                    System.out.println();
                }
                
            } catch (Exception e) {
                logger.severe("Error during weather station operation: " + e.getMessage());
                System.out.println("❌ An error occurred. Please try again.");
                continueOperating = getYesNoInput(scanner, "Continue operating? [y/n]: ");
            }
            
        } while (continueOperating);
        
        System.out.println("\n🙏 Thank you for learning Observer Pattern!");
        System.out.println("👨‍🏫 Key Takeaway: Observer Pattern enables automatic notifications!");
        logger.info("Observer Pattern Weather Station ended");
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
    
    private static void addPhoneDisplay(Scanner scanner, WeatherStation weatherStation) {
        System.out.print("📱 Enter phone name (e.g., iPhone, Samsung): ");
        String phoneName = scanner.nextLine().trim();
        if (phoneName.isEmpty()) phoneName = "Mobile Device";
        
        PhoneDisplay phoneDisplay = new PhoneDisplay(phoneName);
        weatherStation.registerObserver(phoneDisplay);
        System.out.println("📱 " + phoneName + " is now observing weather changes!");
        logger.info("Phone Display added: " + phoneName);
    }
    
    private static void addTVDisplay(Scanner scanner, WeatherStation weatherStation) {
        System.out.print("📺 Enter TV brand (e.g., Samsung, LG, Sony): ");
        String tvBrand = scanner.nextLine().trim();
        if (tvBrand.isEmpty()) tvBrand = "Smart TV";
        
        TVDisplay tvDisplay = new TVDisplay(tvBrand);
        weatherStation.registerObserver(tvDisplay);
        System.out.println("📺 " + tvBrand + " Smart TV is now observing weather changes!");
        logger.info("TV Display added: " + tvBrand);
    }
    
    private static void updateWeatherManually(Scanner scanner, WeatherStation weatherStation) {
        System.out.print("🌡️ Enter temperature (°C): ");
        float temp = getValidFloatInput(scanner, -50, 60);
        System.out.print("💧 Enter humidity (%): ");
        float humidity = getValidFloatInput(scanner, 0, 100);
        System.out.print("📊 Enter pressure (hPa): ");
        float pressure = getValidFloatInput(scanner, 900, 1100);
        
        System.out.println("\n🔄 OBSERVER PATTERN IN ACTION!");
        weatherStation.setWeatherData(temp, humidity, pressure);
        logger.info("Weather data updated manually");
    }
    
    private static float getValidFloatInput(Scanner scanner, float min, float max) {
        int attempts = 0;
        final int maxAttempts = 3;
        
        while (attempts < maxAttempts) {
            try {
                if (scanner.hasNextFloat()) {
                    float input = scanner.nextFloat();
                    scanner.nextLine();
                    
                    if (input >= min && input <= max) {
                        return input;
                    } else {
                        System.out.print("❌ Please enter a value between " + min + " and " + max + ": ");
                    }
                } else {
                    System.out.print("❌ Please enter a valid number: ");
                    scanner.next();
                }
                attempts++;
            } catch (Exception e) {
                logger.warning("Invalid float input: " + e.getMessage());
                attempts++;
                scanner.nextLine();
            }
        }
        
        logger.warning("Max attempts exceeded, using default value");
        return (min + max) / 2; // Return average as default
    }
    
    private static void generateRandomWeather(Random random, WeatherStation weatherStation) {
        float randomTemp = 15 + random.nextFloat() * 20; // 15-35°C
        float randomHumidity = 30 + random.nextFloat() * 50; // 30-80%
        float randomPressure = 980 + random.nextFloat() * 60; // 980-1040 hPa
        
        System.out.println("🎲 Generating random weather data...");
        System.out.println("🔄 OBSERVER PATTERN IN ACTION!");
        weatherStation.setWeatherData(randomTemp, randomHumidity, randomPressure);
        logger.info("Random weather data generated");
    }
    
    private static void showObserverStatus(WeatherStation weatherStation) {
        System.out.println("\n📊 OBSERVER PATTERN STATUS");
        System.out.println("=" * 30);
        System.out.println("🌍 Weather Station: " + weatherStation.getLocation());
        System.out.println("👥 Active Observers: " + weatherStation.getObserverCount());
        System.out.println("\n🔍 Pattern Explanation:");
        System.out.println("   • WeatherStation = Subject (Observable)");
        System.out.println("   • Displays = Observers (Subscribers)");
        System.out.println("   • When weather changes, ALL observers get notified");
        System.out.println("   • Each observer updates its display automatically");
    }
}
