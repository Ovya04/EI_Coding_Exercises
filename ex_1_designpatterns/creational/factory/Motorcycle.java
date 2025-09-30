package ex_1_designpatterns.creational.factory;

public class Motorcycle extends Vehicle {
    public Motorcycle() {
        this.type = "Motorcycle";
        this.wheels = 2;
        this.fuel = "Petrol";
        this.hourlyRate = 8.00;
    }

    @Override
    public void start() {
        System.out.println("🏍️ Motorcycle started with electric start");
        System.out.println("🔊 Engine roaring sound...");
    }

    @Override
    public void stop() {
        System.out.println("🛑 Motorcycle engine stopped");
        System.out.println("🔐 Steering lock engaged");
    }
    
    @Override
    public void displayFeatures() {
        System.out.println("✨ Motorcycle Features:");
        System.out.println("   • Fuel efficient");
        System.out.println("   • Easy parking");
        System.out.println("   • Quick acceleration");
        System.out.println("   • Perfect for city commuting");
    }
}
