package com.jitterted;

import java.util.HashMap;
import java.util.Map;

public enum IngredientName {
  COFFEE("Coffee"),
  SUGAR("Sugar"),
  CREAM("Cream"),
  DECAF_COFFEE("Decaf Coffee"),
  STEAMED_MILK("Steamed Milk"),
  FOAMED_MILK("Foamed Milk"),
  ESPRESSO("Espresso"),
  COCOA("Cocoa"),
  WHIPPED_CREAM("Whipped Cream");

  private final String displayName;

  private static final Map<String, IngredientName> DISPLAY_TO_INGREDIENT = new HashMap<>();

  static {
    for (IngredientName ingredientName : values()) {
      DISPLAY_TO_INGREDIENT.put(ingredientName.displayName, ingredientName);
    }
  }

  IngredientName(String displayName) {
    this.displayName = displayName;
  }

  public static IngredientName fromDisplayName(String ingredientDisplayName) {
    return DISPLAY_TO_INGREDIENT.get(ingredientDisplayName);
  }

  String displayName() {
    return displayName;
  }


}
