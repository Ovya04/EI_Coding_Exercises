package ex_1_designpatterns.structural.bridge;



public class Speakers implements AudioDevice {
    @Override
    public void playAudio(String filename) {
        System.out.println("Playing '" + filename + "' through Speakers with high volume");
    }

    @Override
    public void stopAudio() {
        System.out.println("Stopped playing through Speakers");
    }

    @Override
    public String getDeviceType() {
        return "Speakers";
    }
}
