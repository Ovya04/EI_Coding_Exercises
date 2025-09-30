package ex_1_designpatterns.structural.decorator;

public class WhipDecorator extends CoffeeDecorator {
    public WhipDecorator(Coffee coffee) {
        super(coffee);
    }

    @Override
    public String getDescription() {
        return coffee.getDescription() + " + 🍦 Whipped Cream";
    }

    @Override
    public double getCost() {
        return coffee.getCost() + 0.75;
    }
}
