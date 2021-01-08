package com.jitterted;

import org.junit.jupiter.api.Test;

import java.util.List;

import static com.jitterted.IngredientName.COFFEE;
import static com.jitterted.IngredientName.CREAM;
import static com.jitterted.IngredientName.SUGAR;
import static org.assertj.core.api.Assertions.assertThat;


class RecipeIngredientsTest {

  @Test
  public void ingredientsInRecipeShouldBeIterable() throws Exception {
    Ingredient coffee = new Ingredient(COFFEE, 0.0);
    Ingredient sugar = new Ingredient(SUGAR, 0.0);
    Ingredient cream = new Ingredient(CREAM, 0.0);
    List<Ingredient> ingredientList = List.of(coffee, sugar, cream);
    Recipe recipe = new RecipeFactory(ingredientList).create("Coffee", "Sugar", "Cream");

    assertThat(recipe.ingredients())
        .hasSize(3);
  }

}