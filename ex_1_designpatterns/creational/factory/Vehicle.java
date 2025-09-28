package ex_1_designpatterns.creational.factory;



public abstract class Vehicle {
    protected String type;
    protected int wheels;
    protected String fuel;

    public abstract void start();
    public abstract void stop();

    public void displayInfo() {
        System.out.println("Vehicle Type: " + type);
        System.out.println("Wheels: " + wheels);
        System.out.println("Fuel Type: " + fuel);
    }
}

