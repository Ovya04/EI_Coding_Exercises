package ex_1_designpatterns.structural.decorator;



public class Main {
    public static void main(String[] args) {
        // Start with basic coffee
        Coffee coffee = new BasicCoffee();
        System.out.println(coffee.getDescription() + " - $" + coffee.getCost());

        // Add milk
        coffee = new MilkDecorator(coffee);
        System.out.println(coffee.getDescription() + " - $" + coffee.getCost());

        // Add sugar
        coffee = new SugarDecorator(coffee);
        System.out.println(coffee.getDescription() + " - $" + coffee.getCost());

        // Add whipped cream
        coffee = new WhipDecorator(coffee);
        System.out.println(coffee.getDescription() + " - $" + coffee.getCost());

        System.out.println("\n=== Another Order ===");
        
        // Create a different combination
        Coffee specialCoffee = new WhipDecorator(
            new MilkDecorator(
                new MilkDecorator(
                    new BasicCoffee()
                )
            )
        );
        
        System.out.println(specialCoffee.getDescription() + " - $" + specialCoffee.getCost());
    }
}

