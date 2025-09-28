package ex_1_designpatterns.structural.bridge;



public class Headphones implements AudioDevice {
    @Override
    public void playAudio(String filename) {
        System.out.println("Playing '" + filename + "' through Headphones with personal audio");
    }

    @Override
    public void stopAudio() {
        System.out.println("Stopped playing through Headphones");
    }

    @Override
    public String getDeviceType() {
        return "Headphones";
    }
}

