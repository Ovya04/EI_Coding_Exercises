# Factory Pattern - Vehicle Factory

## Overview
The Factory pattern provides an interface for creating objects without specifying their exact classes. It encapsulates object creation logic.

## Use Case
This example demonstrates creating different types of vehicles (Car, Motorcycle, Bicycle) through a centralized factory.

## Key Components
- **Vehicle**: Abstract base class for all vehicles
- **Car, Motorcycle, Bicycle**: Concrete vehicle implementations
- **VehicleFactory**: Factory class that creates vehicle instances
- **Main**: Demonstrates usage

## Benefits
- Encapsulates object creation logic
- Promotes loose coupling between client and concrete classes
- Makes it easy to add new vehicle types
- Centralizes object creation


