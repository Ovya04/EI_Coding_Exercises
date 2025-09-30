package ex_1_designpatterns.creational.builder;

public class Pizza {
    private String size;
    private boolean cheese;
    private boolean pepperoni;
    private boolean mushrooms;
    private boolean olives;

    private Pizza(PizzaBuilder builder) {
        this.size = builder.size;
        this.cheese = builder.cheese;
        this.pepperoni = builder.pepperoni;
        this.mushrooms = builder.mushrooms;
        this.olives = builder.olives;
    }

    public static class PizzaBuilder {
        private String size;
        private boolean cheese = false;
        private boolean pepperoni = false;
        private boolean mushrooms = false;
        private boolean olives = false;

        public PizzaBuilder(String size) {
            this.size = size;
        }

        public PizzaBuilder addCheese() {
            this.cheese = true;
            return this;
        }

        public PizzaBuilder addPepperoni() {
            this.pepperoni = true;
            return this;
        }

        public PizzaBuilder addMushrooms() {
            this.mushrooms = true;
            return this;
        }

        public PizzaBuilder addOlives() {
            this.olives = true;
            return this;
        }

        public Pizza build() {
            return new Pizza(this);
        }
    }

    public double calculatePrice() {
        double basePrice;
        switch(size.toLowerCase()) {
            case "small": basePrice = 8.99; break;
            case "medium": basePrice = 12.99; break;
            case "large": basePrice = 16.99; break;
            default: basePrice = 12.99; break;
        }
        
        if (cheese) basePrice += 1.50;
        if (pepperoni) basePrice += 2.00;
        if (mushrooms) basePrice += 1.25;
        if (olives) basePrice += 1.00;
        
        return basePrice;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(size).append(" Pizza with:");
        if (!cheese && !pepperoni && !mushrooms && !olives) {
            sb.append(" no toppings (Plain)");
        } else {
            if (cheese) sb.append(" Cheese");
            if (pepperoni) sb.append(" Pepperoni");
            if (mushrooms) sb.append(" Mushrooms");
            if (olives) sb.append(" Olives");
        }
        return sb.toString();
    }
}
