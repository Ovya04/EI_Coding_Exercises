package ex_1_designpatterns.behavioural.observer;

public interface Observer {
    void update(float temperature, float humidity, float pressure);
    String getDisplayName();
    void displayWeather();
}
