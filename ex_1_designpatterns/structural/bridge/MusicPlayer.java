package ex_1_designpatterns.structural.bridge;

public class MusicPlayer extends MediaPlayer {
    public MusicPlayer(AudioDevice device) {
        super(device);
    }

    @Override
    public void play(String filename) {
        System.out.println("🎵 Music Player - Loading track...");
        device.playAudio(filename);
        System.out.println("   ♪ Enjoy your music on " + device.getDeviceType() + "!");
    }

    @Override
    public void stop() {
        System.out.println("⏹️ Music Player - Stopping playback");
        device.stopAudio();
    }
    
    @Override
    public void showPlayerInfo() {
        System.out.println("🎵 Music Player Features:");
        System.out.println("   • High-quality audio playback");
        System.out.println("   • Playlist management");
        System.out.println("   • Equalizer settings");
    }
}
