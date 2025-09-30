package ex_1_designpatterns.behavioural.observer;

public class PhoneDisplay implements Observer {
    private float temperature;
    private float humidity;
    private String deviceName;

    public PhoneDisplay(String deviceName) {
        this.deviceName = deviceName;
    }

    @Override
    public void update(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        displayWeather();
    }

    @Override
    public void displayWeather() {
        System.out.println();
        System.out.println("📱 " + deviceName + " DISPLAY");
        System.out.println("-".repeat(20));
        System.out.printf("🌡️ %.1f°C   💧 %.1f%%%n", temperature, humidity);
        System.out.println("📲 Quick weather summary for mobile");
        
        // Phone-specific features
        if (temperature > 30) {
            System.out.println("🔥 Hot weather alert sent!");
        } else if (temperature < 5) {
            System.out.println("❄️ Cold weather alert sent!");
        }
    }

    @Override
    public String getDisplayName() {
        return "📱 " + deviceName;
    }
}
