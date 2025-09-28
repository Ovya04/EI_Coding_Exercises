package ex_1_designpatterns.creational.factory;



public class VehicleFactory {
    public static Vehicle createVehicle(String vehicleType) {
        if (vehicleType == null || vehicleType.isEmpty()) {
            return null;
        }
        
        switch (vehicleType.toLowerCase()) {
            case "car":
                return new Car();
            case "motorcycle":
                return new Motorcycle();
            case "bicycle":
                return new Bicycle();
            default:
                throw new IllegalArgumentException("Unknown vehicle type: " + vehicleType);
        }
    }
}

