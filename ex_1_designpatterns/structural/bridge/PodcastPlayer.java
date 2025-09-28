package ex_1_designpatterns.structural.bridge;

public class PodcastPlayer extends MediaPlayer {
    public PodcastPlayer(AudioDevice device) {
        super(device);
    }

    @Override
    public void play(String filename) {
        System.out.println("Podcast Player - Loading episode");
        device.playAudio(filename);
        System.out.println("Now playing podcast on " + device.getDeviceType());
    }

    @Override
    public void stop() {
        System.out.println("Podcast Player - Pausing episode");
        device.stopAudio();
    }
}

