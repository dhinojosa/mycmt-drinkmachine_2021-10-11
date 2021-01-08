package com.jitterted;

import org.junit.jupiter.api.Test;

import java.util.List;

import static com.jitterted.IngredientName.COFFEE;
import static com.jitterted.IngredientName.SUGAR;
import static org.assertj.core.api.Assertions.assertThat;

class DrinkStateTest {

  @Test
  public void drinkIsMakeableWhenEnoughInventoryForRecipe() throws Exception {
    Ingredient coffee = new Ingredient(COFFEE, 0.0);
    coffee.setStock(1);
    Ingredient sugar = new Ingredient(SUGAR, 0.0);
    sugar.setStock(2);
    List<Ingredient> ingredients = List.of(coffee, sugar);
    Recipe recipe = new RecipeFactory(ingredients).create("Coffee", "Sugar");
    Drink drink = new Drink("test", recipe);

    drink.updateDrinkState();

    assertThat(drink.getMakeable())
        .isTrue();
  }

  @Test
  public void drinkIsNotMakeableWhenInsufficientInventoryForRecipe() throws Exception {
    Ingredient coffee = new Ingredient(COFFEE, 0.0);
    coffee.setStock(0);
    Ingredient sugar = new Ingredient(SUGAR, 0.0);
    sugar.setStock(2);
    List<Ingredient> ingredients = List.of(coffee, sugar);
    Recipe recipe = new RecipeFactory(ingredients).create("Coffee");
    Drink drink = new Drink("test", recipe);

    drink.updateDrinkState();

    assertThat(drink.getMakeable())
        .isFalse();
  }
}