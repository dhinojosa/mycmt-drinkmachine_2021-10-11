package com.jitterted;

import java.util.HashMap;
import java.util.Map;

public class Recipe {
  private Map<Ingredient, Integer> ingredientMap = new HashMap<>();

  public Recipe(Ingredient... ingredients) {
    for (Ingredient ingredient : ingredients) {
      if (ingredientMap.containsKey(ingredient)) {
        ingredientMap.put(ingredient, ingredientMap.get(ingredient) + 1);
      } else {
        ingredientMap.put(ingredient, 1);
      }
    }
  }

  public boolean hasIngredient(Ingredient ingredient) {
    return ingredientMap.containsKey(ingredient);
  }

  public int quantityNeededFor(Ingredient ingredient) {
    return ingredientMap.get(ingredient);
  }

  public Iterable<Ingredient> ingredients() {
    return () -> ingredientMap.keySet().iterator();
  }
}
