package ex_1_designpatterns.behavioural.observer;



public class TVDisplay implements Observer {
    private float temperature;
    private float pressure;

    @Override
    public void update(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.pressure = pressure;
        display();
    }

    public void display() {
        String condition = "Clear";
        if (pressure < 1000) {
            condition = "Rainy";
        } else if (pressure > 1020) {
            condition = "Sunny";
        }
        
        System.out.println("TV Display: " + temperature + "°C, Condition: " + condition);
    }
}

