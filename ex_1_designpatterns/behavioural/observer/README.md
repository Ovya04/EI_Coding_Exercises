# Observer Pattern - Weather Station

## Overview
The Observer pattern defines a one-to-many dependency between objects so that when one object changes state, all dependents are notified automatically.

## Use Case
This example demonstrates a weather station that notifies multiple display devices when weather data changes.

## Key Components
- **Subject**: Interface for objects that notify observers
- **Observer**: Interface for objects that get notified
- **WeatherStation**: Concrete subject that holds weather data
- **PhoneDisplay, WebDisplay, TVDisplay**: Concrete observers

## Benefits
- Defines loose coupling between subject and observers
- Supports broadcast communication
- Dynamic registration/removal of observers
- Follows Open/Closed Principle

