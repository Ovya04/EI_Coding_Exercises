package ex_1_designpatterns.creational.factory;



public class Car extends Vehicle {
    public Car() {
        this.type = "Car";
        this.wheels = 4;
        this.fuel = "Petrol";
    }

    @Override
    public void start() {
        System.out.println("Car engine started with key ignition");
    }

    @Override
    public void stop() {
        System.out.println("Car engine stopped");
    }
}

