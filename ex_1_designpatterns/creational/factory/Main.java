package ex_1_designpatterns.creational.factory;

public class Main {
    public static void main(String[] args) {
        // Create different vehicles using the factory
        Vehicle car = VehicleFactory.createVehicle("car");
        Vehicle motorcycle = VehicleFactory.createVehicle("motorcycle");
        Vehicle bicycle = VehicleFactory.createVehicle("bicycle");

        // Demonstrate each vehicle
        System.out.println("=== CAR ===");
        car.displayInfo();
        car.start();
        car.stop();

        System.out.println("\n=== MOTORCYCLE ===");
        motorcycle.displayInfo();
        motorcycle.start();
        motorcycle.stop();

        System.out.println("\n=== BICYCLE ===");
        bicycle.displayInfo();
        bicycle.start();
        bicycle.stop();
    }
}
