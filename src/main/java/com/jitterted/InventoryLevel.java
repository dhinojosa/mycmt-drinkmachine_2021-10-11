package com.jitterted;

public class InventoryLevel {
    private int stock = 0;

    public InventoryLevel(int initialStock) {
        if (initialStock < 0) throw new IllegalArgumentException("Inventory cannot be negative");
        stock = initialStock;
    }

    void reduceLevelBy(int quantityNeededFor) {
        //more checks
        this.stock = stock - quantityNeededFor;
    }

    String display() {
       return String.valueOf(stock);
    }

    boolean hasSufficientInventoryFor(int quantityNeededFor) {
        return stock < quantityNeededFor;
    }
}
