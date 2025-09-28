package ex_1_designpatterns.behavioural.observer;


public class Main {
    public static void main(String[] args) {
        // Create weather station (subject)
        WeatherStation weatherStation = new WeatherStation();

        // Create observers (displays)
        PhoneDisplay phoneDisplay = new PhoneDisplay();
        WebDisplay webDisplay = new WebDisplay();
        TVDisplay tvDisplay = new TVDisplay();

        // Register observers
        weatherStation.registerObserver(phoneDisplay);
        weatherStation.registerObserver(webDisplay);
        weatherStation.registerObserver(tvDisplay);

        // Update weather data - all observers will be notified
        weatherStation.setWeatherData(25.0f, 65.0f, 1013.2f);
        weatherStation.setWeatherData(28.0f, 70.0f, 995.5f);
        
        // Remove one observer and update again
        weatherStation.removeObserver(phoneDisplay);
        weatherStation.setWeatherData(22.0f, 80.0f, 1025.1f);
    }
}

