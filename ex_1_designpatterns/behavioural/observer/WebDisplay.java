package ex_1_designpatterns.behavioural.observer;

public class WebDisplay implements Observer {
    private float temperature;
    private float humidity;
    private float pressure;

    @Override
    public void update(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        displayWeather();
    }

    @Override
    public void displayWeather() {
        System.out.println();
        System.out.println("🌐 WEB DASHBOARD");
        System.out.println("-".repeat(25));
        System.out.printf("🌡️ Temperature: %.1f°C%n", temperature);
        System.out.printf("💧 Humidity: %.1f%%%n", humidity);
        System.out.printf("📊 Pressure: %.1f hPa%n", pressure);
        
        // Web-specific analytics
        String condition = getWeatherCondition();
        System.out.println("☁️ Condition: " + condition);
        System.out.println("📈 Historical data updated");
        System.out.println("🔗 Shareable link generated");
    }
    
    private String getWeatherCondition() {
        if (pressure < 1000) return "Stormy ⛈️";
        else if (pressure > 1020) return "Clear ☀️";
        else if (humidity > 80) return "Humid 💧";
        else if (temperature > 25) return "Warm 🌤️";
        else if (temperature < 10) return "Cold ❄️";
        else return "Pleasant 😊";
    }

    @Override
    public String getDisplayName() {
        return "🌐 Web Dashboard";
    }
}
