package ex_1_designpatterns.structural.bridge;

import java.util.Scanner;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continueTesting = true;
        
        System.out.println("=".repeat(50));
        System.out.println("🎵 WELCOME TO BRIDGE PATTERN MEDIA PLAYER 🎵");
        System.out.println("=".repeat(50));
        System.out.println("Learn how Bridge Pattern separates abstraction from implementation!");
        System.out.println();
        
        logger.info("Bridge Pattern Media Player started");
        
        do {
            try {
                // Choose player type
                System.out.println("🎮 Choose your media player:");
                System.out.println("1. 🎵 Music Player");
                System.out.println("2. 🎙️ Podcast Player");
                System.out.print("Enter choice (1-2): ");
                
                int playerChoice = getValidIntInput(scanner, 1, 2);
                
                // Choose audio device
                System.out.println("\n🔊 Choose your audio device:");
                System.out.println("1. 🔊 External Speakers");
                System.out.println("2. 🎧 Headphones");
                System.out.print("Enter choice (1-2): ");
                
                int deviceChoice = getValidIntInput(scanner, 1, 2);
                
                // Create device using Bridge Pattern
                AudioDevice device = createDevice(deviceChoice);
                MediaPlayer player = createPlayer(playerChoice, device);
                
                System.out.println("\n🌉 Bridge Pattern in action!");
                System.out.println("   ✅ Created: " + getPlayerName(playerChoice) + " + " + device.getDeviceType());
                System.out.println("   🔗 Player (Abstraction) bridged to Device (Implementation)");
                
                logger.info("Created player-device combination: " + getPlayerName(playerChoice) + " with " + device.getDeviceType());
                
                // Show player info and volume control
                displayPlayerInfo(player, device);
                
                System.out.print("\n🔊 Set volume (0-" + device.getMaxVolume() + "): ");
                int volume = getValidIntInput(scanner, 0, device.getMaxVolume());
                device.adjustVolume(volume);
                
                // File selection
                System.out.print("\n📁 Enter filename to play (or press Enter for default): ");
                scanner.nextLine(); // consume any remaining newline
                String filename = scanner.nextLine();
                
                if (filename.trim().isEmpty()) {
                    filename = playerChoice == 1 ? "favorite_song.mp3" : "tech_podcast_ep1.mp3";
                    System.out.println("   Using default: " + filename);
                }
                
                // Play media
                System.out.println("\n▶️ Starting playback...");
                System.out.println("-".repeat(30));
                player.play(filename);
                
                System.out.print("\nPress Enter to stop playback...");
                scanner.nextLine();
                
                System.out.println("-".repeat(30));
                player.stop();
                
                displayPatternExplanation();
                
                continueTesting = getYesNoInput(scanner, "\n🔄 Try another combination? [y/n]: ");
                
            } catch (Exception e) {
                logger.severe("Error during media player operation: " + e.getMessage());
                System.out.println("❌ An error occurred. Please try again.");
                continueTesting = getYesNoInput(scanner, "Continue with new setup? [y/n]: ");
            }
            
        } while (continueTesting);
        
        System.out.println("\n🙏 Thank you for learning Bridge Pattern with us!");
        System.out.println("👨‍🏫 Key Takeaway: Bridge Pattern separates what you do from how you do it!");
        logger.info("Bridge Pattern Media Player ended");
        scanner.close();
    }
    
    private static int getValidIntInput(Scanner scanner, int min, int max) {
        int attempts = 0;
        final int maxAttempts = 3;
        
        while (attempts < maxAttempts) {
            try {
                if (scanner.hasNextInt()) {
                    int input = scanner.nextInt();
                    
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
    
    private static AudioDevice createDevice(int choice) {
        switch (choice) {
            case 1: return new Speakers();
            case 2: return new Headphones();
            default: return new Speakers();
        }
    }
    
    private static MediaPlayer createPlayer(int choice, AudioDevice device) {
        switch (choice) {
            case 1: return new MusicPlayer(device);
            case 2: return new PodcastPlayer(device);
            default: return new MusicPlayer(device);
        }
    }
    
    private static String getPlayerName(int choice) {
        switch (choice) {
            case 1: return "Music Player";
            case 2: return "Podcast Player";
            default: return "Music Player";
        }
    }
    
    private static void displayPlayerInfo(MediaPlayer player, AudioDevice device) {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("📱 YOUR MEDIA SETUP");
        System.out.println("=".repeat(40));
        player.showPlayerInfo();
        System.out.println("🔊 Output Device: " + device.getDeviceType());
        System.out.println("📊 Max Volume: " + device.getMaxVolume());
    }
    
    private static void displayPatternExplanation() {
        System.out.println("\n🔍 Bridge Pattern Explanation:");
        System.out.println("   • Abstraction: MediaPlayer (MusicPlayer/PodcastPlayer)");
        System.out.println("   • Implementation: AudioDevice (Speakers/Headphones)");
        System.out.println("   • Bridge: Player holds reference to Device");
        System.out.println("   • Benefits: Can combine any Player with any Device!");
    }
}
