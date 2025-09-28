package ex_1_designpatterns.creational.factory;



public class Motorcycle extends Vehicle {
    public Motorcycle() {
        this.type = "Motorcycle";
        this.wheels = 2;
        this.fuel = "Petrol";
    }

    @Override
    public void start() {
        System.out.println("Motorcycle started with kick/electric start");
    }

    @Override
    public void stop() {
        System.out.println("Motorcycle engine stopped");
    }
}

