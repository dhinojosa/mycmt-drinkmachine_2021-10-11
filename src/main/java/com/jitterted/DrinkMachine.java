package com.jitterted;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.jitterted.IngredientName.COCOA;
import static com.jitterted.IngredientName.COFFEE;
import static com.jitterted.IngredientName.CREAM;
import static com.jitterted.IngredientName.DECAF_COFFEE;
import static com.jitterted.IngredientName.ESPRESSO;
import static com.jitterted.IngredientName.FOAMED_MILK;
import static com.jitterted.IngredientName.STEAMED_MILK;
import static com.jitterted.IngredientName.SUGAR;
import static com.jitterted.IngredientName.WHIPPED_CREAM;

public class DrinkMachine {

  private final List<Drink> drinkList = new ArrayList<>();
  private final List<Ingredient> ingredientList = new ArrayList<>();

  public static void main(String[] args) {
    DrinkMachine drinkMachine = new DrinkMachine();
    drinkMachine.displayInventoryAndMenu();
    drinkMachine.startIO();
  }

  public DrinkMachine() {
    ingredientList.add(new Ingredient(COFFEE, 0.75));
    ingredientList.add(new Ingredient(DECAF_COFFEE, 0.75));
    ingredientList.add(new Ingredient(SUGAR, 0.25));
    ingredientList.add(new Ingredient(CREAM, 0.25));
    ingredientList.add(new Ingredient(STEAMED_MILK, 0.35));
    ingredientList.add(new Ingredient(FOAMED_MILK, 0.35));
    ingredientList.add(new Ingredient(ESPRESSO, 1.10));
    ingredientList.add(new Ingredient(COCOA, 0.90));
    ingredientList.add(new Ingredient(WHIPPED_CREAM, 1.00));

    Collections.sort(ingredientList);

    RecipeFactory recipeFactory = new RecipeFactory(ingredientList);
    drinkList.add(new Drink("Coffee", recipeFactory.create("Coffee", "Coffee", "Coffee", "Sugar", "Cream")));
    drinkList.add(new Drink("Decaf Coffee", recipeFactory.create("Decaf Coffee", "Decaf Coffee", "Decaf Coffee", "Sugar", "Cream")));
    drinkList.add(new Drink("Caffe Latte", recipeFactory.create("Espresso", "Espresso", "Steamed Milk")));
    drinkList.add(new Drink("Caffe Americano", recipeFactory.create("Espresso", "Espresso", "Espresso")));
    drinkList.add(new Drink("Caffe Mocha", recipeFactory.create("Espresso", "Cocoa", "Steamed Milk", "Whipped Cream")));
    drinkList.add(new Drink("Cappuccino", recipeFactory.create("Espresso", "Espresso", "Steamed Milk", "Foamed Milk")));

    Collections.sort(drinkList);

    for (Drink drink : drinkList) {
      double cost = 0;
      Recipe recipe = drink.getRecipe();
      for (Ingredient ingredient : ingredientList) {
        if (recipe.hasIngredient(ingredient)) {
          cost += ingredient.getCost() * recipe.quantityNeededFor(ingredient);
        }
      }
      drink.setCost(cost);
    }
    updateMakeable();
  }

  public void startIO() {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    String input = "";

    while (true) {
      try {
        input = reader.readLine().toLowerCase();
        if (input.isBlank()) {
          continue;
        } else if (input.equals("q")) {
          System.exit(0);
        } else if (input.equals("r")) {
          restockIngredients();
          updateMakeable();
        } else if (Integer.parseInt(input) > 0 && Integer.parseInt(input) <= drinkList.size()) {
          makeDrink(drinkList.get(Integer.parseInt(input) - 1));
        } else {
          System.out.println("'" + input + "' was not valid. Choose from list above, or Q or R.");
        }
      } catch (IOException e) {
        System.out.println("No idea why we got an IOException here." + e);
      }
    }
  }

  public void displayInventoryAndMenu() {
    System.out.println("\nIngredient Inventory:\n");
    for (Ingredient ingredient : ingredientList) {
      System.out.println(ingredient.getName().displayName() + ", " + ingredient.getStock());
    }

    System.out.println("\nDrink Menu:\n");
    int count = 1;
    for (Drink d : drinkList) {
      System.out.printf("%d,%s,$%.2f," + d.getMakeable() + "\n\n", count, d.getName(), d.getCost());
      count++;
    }
  }

  public void makeDrink(Drink drink) {
    if (drink.getMakeable()) {
      System.out.println("Dispensing: " + drink.getName() + "\n");
      for (Ingredient ingredient : ingredientList) {
        Recipe recipe = drink.getRecipe();
        if (recipe.hasIngredient(ingredient)) {
          ingredient.setStock(ingredient.getStock() - recipe.quantityNeededFor(ingredient));
        }
      }
    } else {
      System.out.println("Out of stock: " + drink.getName() + "\n");
    }
    updateMakeable();
    displayInventoryAndMenu();
  }

  public void restockIngredients() {
    for (Ingredient ingredient : ingredientList) {
      ingredient.setStock(10);
    }
    updateMakeable();
    displayInventoryAndMenu();
  }

  private void updateMakeable() {
    for (Drink drink : drinkList) {
      drink.updateDrinkState();
    }
  }

}