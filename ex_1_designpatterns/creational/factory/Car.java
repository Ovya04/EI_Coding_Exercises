package ex_1_designpatterns.creational.factory;

public class Car extends Vehicle {
    public Car() {
        this.type = "Car";
        this.wheels = 4;
        this.fuel = "Petrol";
        this.hourlyRate = 15.00;
    }

    @Override
    public void start() {
        System.out.println("🔑 Car engine started with key ignition");
        System.out.println("🎵 Playing your favorite radio station...");
    }

    @Override
    public void stop() {
        System.out.println("🛑 Car engine stopped");
        System.out.println("🔒 Doors automatically locked");
    }
    
    @Override
    public void displayFeatures() {
        System.out.println("✨ Car Features:");
        System.out.println("   • Air Conditioning");
        System.out.println("   • GPS Navigation");
        System.out.println("   • Bluetooth Audio");
        System.out.println("   • Comfortable seating for 4-5 people");
    }
}
