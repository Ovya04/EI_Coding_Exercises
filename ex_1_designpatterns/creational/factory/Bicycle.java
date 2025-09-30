package ex_1_designpatterns.creational.factory;

public class Bicycle extends Vehicle {
    public Bicycle() {
        this.type = "Bicycle";
        this.wheels = 2;
        this.fuel = "Human Power";
        this.hourlyRate = 3.00;
    }

    @Override
    public void start() {
        System.out.println("🚴 Started pedaling the bicycle");
        System.out.println("🌿 Eco-friendly ride begins...");
    }

    @Override
    public void stop() {
        System.out.println("🛑 Stopped pedaling and applied brakes");
        System.out.println("🅿️ Bicycle safely parked");
    }
    
    @Override
    public void displayFeatures() {
        System.out.println("✨ Bicycle Features:");
        System.out.println("   • Zero emissions");
        System.out.println("   • Great exercise");
        System.out.println("   • No fuel costs");
        System.out.println("   • Easy maintenance");
    }
}
