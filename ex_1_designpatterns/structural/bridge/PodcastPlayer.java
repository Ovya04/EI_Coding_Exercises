package ex_1_designpatterns.structural.bridge;

public class PodcastPlayer extends MediaPlayer {
    public PodcastPlayer(AudioDevice device) {
        super(device);
    }

    @Override
    public void play(String filename) {
        System.out.println("🎙️ Podcast Player - Loading episode...");
        device.playAudio(filename);
        System.out.println("   📻 Learning time on " + device.getDeviceType() + "!");
    }

    @Override
    public void stop() {
        System.out.println("⏸️ Podcast Player - Pausing episode (bookmark saved)");
        device.stopAudio();
    }
    
    @Override
    public void showPlayerInfo() {
        System.out.println("🎙️ Podcast Player Features:");
        System.out.println("   • Episode bookmarking");
        System.out.println("   • Speed control (1x, 1.5x, 2x)");
        System.out.println("   • Sleep timer");
    }
}
