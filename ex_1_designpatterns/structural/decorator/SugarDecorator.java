package ex_1_designpatterns.structural.decorator;

public class SugarDecorator extends CoffeeDecorator {
    public SugarDecorator(Coffee coffee) {
        super(coffee);
    }

    @Override
    public String getDescription() {
        return coffee.getDescription() + " + 🍯 Sugar";
    }

    @Override
    public double getCost() {
        return coffee.getCost() + 0.25;
    }
}
