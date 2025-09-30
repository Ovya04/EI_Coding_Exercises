package ex_1_designpatterns.structural.bridge;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=".repeat(50));
        System.out.println("🎵 WELCOME TO BRIDGE PATTERN MEDIA PLAYER 🎵");
        System.out.println("=".repeat(50));
        System.out.println("Learn how Bridge Pattern separates abstraction from implementation!");
        System.out.println();
        
        while (true) {
            try {
                // Choose player type
                System.out.println("🎮 Choose your media player:");
                System.out.println("1. 🎵 Music Player");
                System.out.println("2. 🎙️ Podcast Player");
                System.out.println("3. ❌ Exit");
                System.out.print("Enter choice (1-3): ");
                
                int playerChoice = scanner.nextInt();
                scanner.nextLine(); // consume newline
                
                if (playerChoice == 3) break;
                
                if (playerChoice < 1 || playerChoice > 2) {
                    System.out.println("❌ Invalid choice! Please try again.");
                    continue;
                }
                
                // Choose audio device
                System.out.println();
                System.out.println("🔊 Choose your audio device:");
                System.out.println("1. 🔊 External Speakers");
                System.out.println("2. 🎧 Headphones");
                System.out.print("Enter choice (1-2): ");
                
                int deviceChoice = scanner.nextInt();
                scanner.nextLine(); // consume newline
                
                // Create device using Bridge Pattern
                AudioDevice device;
                switch (deviceChoice) {
                    case 1: device = new Speakers(); break;
                    case 2: device = new Headphones(); break;
                    default:
                        System.out.println("❌ Invalid choice! Using Speakers as default.");
                        device = new Speakers();
                        break;
                }
                
                // Create player using Bridge Pattern
                MediaPlayer player;
                String playerType;
                switch (playerChoice) {
                    case 1: 
                        player = new MusicPlayer(device);
                        playerType = "Music Player";
                        break;
                    case 2: 
                        player = new PodcastPlayer(device);
                        playerType = "Podcast Player";
                        break;
                    default: 
                        player = new MusicPlayer(device);
                        playerType = "Music Player";
                        break;
                }
                
                System.out.println();
                System.out.println("🌉 Bridge Pattern in action!");
                System.out.println("   ✅ Created: " + playerType + " + " + device.getDeviceType());
                System.out.println("   🔗 Player (Abstraction) bridged to Device (Implementation)");
                
                // Show player info
                System.out.println();
                System.out.println("==================================");
                System.out.println("📱 YOUR MEDIA SETUP");
                System.out.println("==================================");
                player.showPlayerInfo();
                System.out.println("🔊 Output Device: " + device.getDeviceType());
                System.out.println("📊 Max Volume: " + device.getMaxVolume());
                
                // Volume control
                System.out.println();
                System.out.print("🔊 Set volume (0-" + device.getMaxVolume() + "): ");
                int volume = scanner.nextInt();
                scanner.nextLine(); // consume newline
                device.adjustVolume(volume);
                
                // File selection
                System.out.println();
                System.out.print("📁 Enter filename to play: ");
                String filename = scanner.nextLine();
                
                if (filename.trim().isEmpty()) {
                    filename = playerChoice == 1 ? "favorite_song.mp3" : "tech_podcast_ep1.mp3";
                    System.out.println("   Using default: " + filename);
                }
                
                // Play media
                System.out.println();
                System.out.println("▶️ Starting playback...");
                System.out.println("-".repeat(30));
                player.play(filename);
                
                System.out.println();
                System.out.print("Press Enter to stop playback...");
                scanner.nextLine();
                
                System.out.println("-".repeat(30));
                player.stop();
                
                System.out.println();
                System.out.println("🔍 Bridge Pattern Explanation:");
                System.out.println("   • Abstraction: MediaPlayer (MusicPlayer/PodcastPlayer)");
                System.out.println("   • Implementation: AudioDevice (Speakers/Headphones)");
                System.out.println("   • Bridge: Player holds reference to Device");
                System.out.println("   • Benefits: Can combine any Player with any Device!");
                System.out.println("   • Example: MusicPlayer + Headphones, PodcastPlayer + Speakers");
                
                System.out.println();
                System.out.print("🔄 Try another combination? [y/n]: ");
                if (!scanner.nextLine().toLowerCase().startsWith("y")) {
                    break;
                }
                System.out.println();
                
            } catch (Exception e) {
                System.out.println("❌ Invalid input! Please try again.");
                scanner.nextLine(); // Clear invalid input
            }
        }
        
        System.out.println();
        System.out.println("🙏 Thank you for learning Bridge Pattern with us!");
        System.out.println("👨‍🏫 Key Takeaway: Bridge Pattern separates what you do from how you do it!");
        scanner.close();
    }
}
