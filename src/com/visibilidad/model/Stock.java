package com.visibilidad.model;

import java.util.Objects;

public class Stock {
    private int sizeId;
    private int quantity;

    public Stock(int sizeId, int quantity) {
        this.sizeId = sizeId;
        this.quantity = quantity;
    }

    public int getSizeId() {
        return sizeId;
    }

    public void setSizeId(int sizeId) {
        this.sizeId = sizeId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stock stock = (Stock) o;
        return sizeId == stock.sizeId && quantity == stock.quantity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sizeId, quantity);
    }
}