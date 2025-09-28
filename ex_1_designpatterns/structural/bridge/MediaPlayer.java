package ex_1_designpatterns.structural.bridge;



public abstract class MediaPlayer {
    protected AudioDevice device;

    public MediaPlayer(AudioDevice device) {
        this.device = device;
    }

    public abstract void play(String filename);
    public abstract void stop();
}
