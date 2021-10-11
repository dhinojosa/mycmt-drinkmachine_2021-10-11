package com.jitterted;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.jitterted.IngredientName.*;

public class DrinkMachine {

    private final List<Drink> drinkList = new ArrayList<>();
    private final List<Ingredient> ingredientList = new ArrayList<>();

    public static void main(String[] args) {
        DrinkMachine drinkMachine = new DrinkMachine();
        drinkMachine.displayInventoryAndMenu();
        drinkMachine.startIO();
    }

    public DrinkMachine() {
        createIngredientList();
        initializeDrinkList();
        updateDrinkCosts();
        updateMakeable();
    }

    private void updateDrinkCosts() {
        for (Drink drink : drinkList) {
            drink.setCost(calculateCost(drink));
        }
    }

    private double calculateCost(Drink drink) {
        double cost = 0;
        Recipe recipe = drink.getRecipe();
        for (Ingredient ingredient : ingredientList) {
            if (recipe.hasIngredient(ingredient)) {
                cost += ingredient.getCost() * recipe.quantityNeededFor(ingredient);
            }
        }
        return cost;
    }

    private void initializeDrinkList() {
        RecipeFactory recipeFactory = new RecipeFactory(ingredientList);
        drinkList.add(new Drink("Coffee", recipeFactory.create("Coffee",
            "Coffee", "Coffee", "Sugar", "Cream")));
        drinkList.add(new Drink("Decaf Coffee", recipeFactory.create("Decaf " +
            "Coffee", "Decaf Coffee", "Decaf Coffee", "Sugar", "Cream")));
        drinkList.add(new Drink("Caffe Latte", recipeFactory.create("Espresso"
            , "Espresso", "Steamed Milk")));
        drinkList.add(new Drink("Caffe Americano", recipeFactory.create(
            "Espresso", "Espresso", "Espresso")));
        drinkList.add(new Drink("Caffe Mocha", recipeFactory.create("Espresso"
            , "Cocoa", "Steamed Milk", "Whipped Cream")));
        drinkList.add(new Drink("Cappuccino", recipeFactory.create("Espresso"
            , "Espresso", "Steamed Milk", "Foamed Milk")));

        Collections.sort(drinkList);
    }

    private void createIngredientList() {
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
    }

    public void startIO() {
        BufferedReader reader =
            new BufferedReader(new InputStreamReader(System.in));
        String input = "";

        while (true) {
            try {
                input = reader.readLine().toLowerCase();
                if (input.isBlank()) {
                    continue;
                } else if (userQuits(input)) {
                    System.exit(0);
                } else if (restockRequested(input)) {
                    restockIngredients();
                    updateMakeable();
                } else if (drinkSelectionIsMade(input)) {
                    makeDrink(drinkList.get(Integer.parseInt(input) - 1));
                } else {
                    System.out.println("'" + input + "' was not valid. Choose" +
                        " from list above, or Q or R.");
                }
            } catch (IOException e) {
                System.out.println("No idea why we got an IOException here." + e);
            }
        }
    }

    private boolean drinkSelectionIsMade(String input) {
        return Integer.parseInt(input) > 0 && Integer.parseInt(input) <= drinkList.size();
    }

    private boolean restockRequested(String input) {
        return input.equals("r");
    }

    private boolean userQuits(String input) {
        return input.equals("q");
    }

    public void displayInventoryAndMenu() {
        displayInventory();
        displayMenu();
    }

    private void displayMenu() {
        System.out.println("\nDrink Menu:\n");
        int count = 1;
        for (Drink d : drinkList) {
            System.out.printf("%d,%s,$%.2f," + d.getMakeable() + "\n\n",
                count, d.getName(), d.getCost());
            count++;
        }
    }

    private void displayInventory() {
        System.out.println("\nIngredient Inventory:\n");
        for (Ingredient ingredient : ingredientList) {
            System.out.println(ingredient.getName().displayName() + ", " + ingredient.getStock());
        }
    }

    public void makeDrink(Drink drink) {
        dispenseDrink(drink);
        updateMakeable();
        displayInventoryAndMenu();
    }

    private void dispenseDrink(Drink drink) {
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
    }

    public void restockIngredients() {
        resetStock();
        updateMakeable();
        displayInventoryAndMenu();
    }

    private void resetStock() {
        for (Ingredient ingredient : ingredientList) {
            ingredient.setStock(10);
        }
    }

    private void updateMakeable() {
        for (Drink drink : drinkList) {
            drink.updateDrinkState();
        }
    }
}
