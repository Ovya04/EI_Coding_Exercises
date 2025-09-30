package ex_1_designpatterns.behavioural.observer;

import java.util.ArrayList;
import java.util.List;

public class WeatherStation implements Subject {
    private List<Observer> observers;
    private float temperature;
    private float humidity;
    private float pressure;
    private String location;

    public WeatherStation(String location) {
        this.location = location;
        observers = new ArrayList<>();
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
        System.out.println("✅ " + observer.getDisplayName() + " subscribed to weather updates");
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
        System.out.println("❌ " + observer.getDisplayName() + " unsubscribed from weather updates");
    }

    @Override
    public void notifyObservers() {
        System.out.println("📡 Broadcasting weather update to " + observers.size() + " displays...");
        for (Observer observer : observers) {
            observer.update(temperature, humidity, pressure);
        }
    }

    public void setWeatherData(float temperature, float humidity, float pressure) {
        System.out.println();
        System.out.println("🌡️ WEATHER DATA UPDATED AT " + location.toUpperCase());
        System.out.println("============================");
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        
        System.out.printf("🌡️ Temperature: %.1f°C%n", temperature);
        System.out.printf("💧 Humidity: %.1f%%%n", humidity);
        System.out.printf("📊 Pressure: %.1f hPa%n", pressure);
        System.out.println("============================");
        
        notifyObservers();
    }
    
    public String getLocation() {
        return location;
    }
    
    public int getObserverCount() {
        return observers.size();
    }
}
