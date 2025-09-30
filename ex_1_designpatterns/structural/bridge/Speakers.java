package ex_1_designpatterns.structural.bridge;

public class Speakers implements AudioDevice {
    private int currentVolume = 50;
    
    @Override
    public void playAudio(String filename) {
        System.out.println("🔊 Playing '" + filename + "' through Speakers");
        System.out.println("   📢 Room-filling sound at volume " + currentVolume);
    }

    @Override
    public void stopAudio() {
        System.out.println("🔇 Stopped playing through Speakers");
    }

    @Override
    public String getDeviceType() {
        return "External Speakers";
    }
    
    @Override
    public void adjustVolume(int volume) {
        this.currentVolume = Math.min(Math.max(volume, 0), 100);
        System.out.println("🔊 Speaker volume set to: " + currentVolume);
    }
    
    @Override
    public int getMaxVolume() {
        return 100;
    }
}
