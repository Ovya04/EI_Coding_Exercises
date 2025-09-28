package ex_1_designpatterns.creational.factory;


public class Bicycle extends Vehicle {
    public Bicycle() {
        this.type = "Bicycle";
        this.wheels = 2;
        this.fuel = "Human Power";
    }

    @Override
    public void start() {
        System.out.println("Started pedaling the bicycle");
    }

    @Override
    public void stop() {
        System.out.println("Stopped pedaling and applied brakes");
    }
}

