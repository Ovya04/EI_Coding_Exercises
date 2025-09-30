package ex_1_designpatterns.structural.bridge;

public interface AudioDevice {
    void playAudio(String filename);
    void stopAudio();
    String getDeviceType();
    void adjustVolume(int volume);
    int getMaxVolume();
}
