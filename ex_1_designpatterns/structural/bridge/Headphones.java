package ex_1_designpatterns.structural.bridge;

public class Headphones implements AudioDevice {
    private int currentVolume = 30;
    
    @Override
    public void playAudio(String filename) {
        System.out.println("🎧 Playing '" + filename + "' through Headphones");
        System.out.println("   🎵 Personal audio experience at volume " + currentVolume);
    }

    @Override
    public void stopAudio() {
        System.out.println("🔇 Stopped playing through Headphones");
    }

    @Override
    public String getDeviceType() {
        return "Headphones";
    }
    
    @Override
    public void adjustVolume(int volume) {
        this.currentVolume = Math.min(Math.max(volume, 0), 80);
        System.out.println("🎧 Headphone volume set to: " + currentVolume + " (Safe listening level)");
    }
    
    @Override
    public int getMaxVolume() {
        return 80; // Lower max volume for hearing protection
    }
}
