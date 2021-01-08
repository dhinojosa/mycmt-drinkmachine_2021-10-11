package com.jitterted;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecipeFactory {
  private final Map<IngredientName, Ingredient> nameToIngredient = new HashMap<>();

  public RecipeFactory(List<Ingredient> ingredientList) {
    for (Ingredient ingredient : ingredientList) {
      nameToIngredient.put(ingredient.getName(), ingredient);
    }
  }

  public Recipe create(String... ingredientDisplayNames) {
    Ingredient[] ingredients = new Ingredient[ingredientDisplayNames.length];
    for (int i = 0; i < ingredientDisplayNames.length; i++) {
      IngredientName ingredientName = IngredientName.fromDisplayName(ingredientDisplayNames[i]);
      ingredients[i] = nameToIngredient.get(ingredientName);
    }
    return new Recipe(ingredients);
  }
}
