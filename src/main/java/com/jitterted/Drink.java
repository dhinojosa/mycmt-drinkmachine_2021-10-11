package com.jitterted;

public class Drink implements Comparable<Drink> {
  private final Recipe recipe;
  private String name;
  private double totalCost = 0;
  private boolean makeable = false;

  public Drink(String name, Recipe recipe) {
    this.name = name;
    this.recipe = recipe;
  }

  public void updateDrinkState() {
    Recipe recipe = getRecipe();
    for (Ingredient ingredient : recipe.ingredients()) {
      if (ingredient.getStock() < recipe.quantityNeededFor(ingredient)) {
        setMakeable(false);
        return;
      }
      setMakeable(true);
    }
  }

  public int compareTo(Drink drink) {
    return name.compareTo(drink.getName());
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setCost(double totalCost) {
    this.totalCost = totalCost;
  }

  public void setMakeable(boolean makeable) {
    this.makeable = makeable;
  }

  public Recipe getRecipe() {
    return recipe;
  }

  public double getCost() {
    return totalCost;
  }

  public String getName() {
    return name;
  }

  public boolean getMakeable() {
    return makeable;
  }

}