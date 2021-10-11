package com.jitterted;

public class Ingredient implements Comparable<Ingredient> {
    private final IngredientName name;
    private final double cost; //primitive obsession
    private InventoryLevel inventoryLevel;

    public Ingredient(IngredientName name, double cost) {
        this(name, cost, 10);
    }

    public Ingredient(IngredientName name, double cost, int initialStock) {
        this.name = name;
        this.cost = cost;
        this.inventoryLevel = new InventoryLevel(initialStock);
    }

    public int compareTo(Ingredient ingredient) {
        return name.compareTo(ingredient.name);
    }

    public double getCost() {
        return cost;
    }

    public IngredientName getName() {
        return name;
    }

    boolean hasSufficientInventory(Recipe recipe) {
        return inventoryLevel.hasSufficientInventoryFor(recipe.quantityNeededFor(this));
    }

    void reduceInventory(Recipe recipe) {
        inventoryLevel.reduceLevelBy(recipe.quantityNeededFor(this));
    }

    String displayStock() {
        return inventoryLevel.display();
    }

    void reset() {
        this.inventoryLevel = new InventoryLevel(10);
    }
}
