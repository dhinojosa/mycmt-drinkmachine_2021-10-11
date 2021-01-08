package com.jitterted;

import org.junit.jupiter.api.Test;

import java.util.List;

import static com.jitterted.IngredientName.COFFEE;
import static com.jitterted.IngredientName.CREAM;
import static com.jitterted.IngredientName.SUGAR;
import static org.assertj.core.api.Assertions.assertThat;

public class RecipeFactoryTest {

  @Test
  public void createRecipeWithMultipleIngredientsReturnsRecipe() throws Exception {
    Ingredient coffee = new Ingredient(COFFEE, 0.0);
    Ingredient sugar = new Ingredient(SUGAR, 0.0);
    Ingredient cream = new Ingredient(CREAM, 0.0);
    List<Ingredient> ingredientList = List.of(coffee, sugar, cream);
    Recipe recipe = new RecipeFactory(ingredientList).create("Coffee", "Coffee", "Coffee", "Sugar", "Cream");

    assertThat(recipe.hasIngredient(coffee))
        .isTrue();
    assertThat(recipe.quantityNeededFor(coffee))
        .isEqualTo(3);
    assertThat(recipe.hasIngredient(sugar))
        .isTrue();
    assertThat(recipe.hasIngredient(cream))
        .isTrue();
  }
}
