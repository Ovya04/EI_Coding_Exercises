package ex_1_designpatterns.structural.bridge;


public class Main {
    public static void main(String[] args) {
        // Create different audio devices
        AudioDevice speakers = new Speakers();
        AudioDevice headphones = new Headphones();

        // Create different media players with different devices
        MediaPlayer musicOnSpeakers = new MusicPlayer(speakers);
        MediaPlayer podcastOnHeadphones = new PodcastPlayer(headphones);
        MediaPlayer musicOnHeadphones = new MusicPlayer(headphones);

        System.out.println("=== Music on Speakers ===");
        musicOnSpeakers.play("favorite_song.mp3");
        musicOnSpeakers.stop();

        System.out.println("\n=== Podcast on Headphones ===");
        podcastOnHeadphones.play("tech_podcast_ep1.mp3");
        podcastOnHeadphones.stop();

        System.out.println("\n=== Music on Headphones ===");
        musicOnHeadphones.play("classical_music.mp3");
        musicOnHeadphones.stop();
    }
}

