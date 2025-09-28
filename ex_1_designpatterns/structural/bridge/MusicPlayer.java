package ex_1_designpatterns.structural.bridge;


public class MusicPlayer extends MediaPlayer {
    public MusicPlayer(AudioDevice device) {
        super(device);
    }

    @Override
    public void play(String filename) {
        System.out.println("Music Player - Starting playback");
        device.playAudio(filename);
        System.out.println("Now playing music on " + device.getDeviceType());
    }

    @Override
    public void stop() {
        System.out.println("Music Player - Stopping playback");
        device.stopAudio();
    }
}

