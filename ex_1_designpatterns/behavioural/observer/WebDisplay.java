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
        display();
    }

    public void display() {
        System.out.println("Web Display: " + temperature + "°C, " + 
                         humidity + "% humidity, " + pressure + " hPa");
    }
}

