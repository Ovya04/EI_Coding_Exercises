package ex_1_designpatterns.behavioural.observer;

public class TVDisplay implements Observer {
    private float temperature;
    private float pressure;
    private String tvBrand;

    public TVDisplay(String tvBrand) {
        this.tvBrand = tvBrand;
    }

    @Override
    public void update(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.pressure = pressure;
        displayWeather();
    }

    @Override
    public void displayWeather() {
        System.out.println();
        System.out.println("📺 " + tvBrand.toUpperCase() + " SMART TV");
        System.out.println("-".repeat(30));
        System.out.printf("🌡️ Temperature: %.1f°C%n", temperature);
        
        String forecast = getForecast();
        System.out.println("🔮 Forecast: " + forecast);
        
        // TV-specific features
        System.out.println("📺 Weather widget updated on home screen");
        System.out.println("🎵 Background music adjusted to weather mood");
    }
    
    private String getForecast() {
        if (pressure < 995) return "Rain expected ☔";
        else if (pressure > 1025) return "Sunny skies ahead ☀️";
        else if (temperature > 30) return "Stay hydrated! 🥤";
        else if (temperature < 5) return "Bundle up! 🧥";
        else return "Perfect weather! 😊";
    }

    @Override
    public String getDisplayName() {
        return "📺 " + tvBrand + " Smart TV";
    }
}
