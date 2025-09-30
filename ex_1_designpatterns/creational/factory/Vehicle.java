package ex_1_designpatterns.creational.factory;

public abstract class Vehicle {
    protected String type;
    protected int wheels;
    protected String fuel;
    protected double hourlyRate;

    public abstract void start();
    public abstract void stop();
    public abstract void displayFeatures();

    public void displayInfo() {
        System.out.println("🚗 Vehicle Type: " + type);
        System.out.println("🛞 Wheels: " + wheels);
        System.out.println("⛽ Fuel Type: " + fuel);
        System.out.printf("💰 Hourly Rate: $%.2f%n", hourlyRate);
    }
    
    public double getHourlyRate() {
        return hourlyRate;
    }
}
