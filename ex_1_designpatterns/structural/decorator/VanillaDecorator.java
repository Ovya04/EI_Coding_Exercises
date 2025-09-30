package ex_1_designpatterns.structural.decorator;

public class VanillaDecorator extends CoffeeDecorator {
    public VanillaDecorator(Coffee coffee) {
        super(coffee);
    }

    @Override
    public String getDescription() {
        return coffee.getDescription() + " + 🌟 Vanilla Syrup";
    }

    @Override
    public double getCost() {
        return coffee.getCost() + 0.60;
    }
}
