package ex_1_designpatterns.creational.builder;

public class Main {
    public static void main(String[] args) {
        // Build different pizzas using the builder pattern
        Pizza margherita = new Pizza.PizzaBuilder("Medium")
                .addCheese()
                .build();

        Pizza deluxe = new Pizza.PizzaBuilder("Large")
                .addCheese()
                .addPepperoni()
                .addMushrooms()
                .addOlives()
                .build();

        System.out.println("Margherita Pizza: " + margherita);
        System.out.println("Deluxe Pizza: " + deluxe);
    }
}
