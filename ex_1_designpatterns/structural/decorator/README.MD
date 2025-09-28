# Decorator Pattern - Coffee Shop

## Overview
The Decorator pattern allows behavior to be added to objects dynamically without altering their structure.

## Use Case
This example demonstrates a coffee shop system where you can add various ingredients (milk, sugar, whipped cream) to basic coffee.

## Key Components
- **Coffee**: Base interface for coffee objects
- **BasicCoffee**: Concrete implementation of basic coffee
- **CoffeeDecorator**: Abstract decorator class
- **MilkDecorator, SugarDecorator, WhipDecorator**: Concrete decorators

## Benefits
- Adds functionality dynamically
- More flexible than inheritance
- Allows multiple decorations
- Follows Open/Closed Principle

