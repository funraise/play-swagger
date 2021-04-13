package com.example.dto;

import java.util.Objects;

public final class ExampleRequest {
    private final String name;
    private final int amount;

    public ExampleRequest(String name, int amount) {
        this.name = name;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (ExampleRequest) obj;
        return Objects.equals(this.name, that.name) &&
                this.amount == that.amount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, amount);
    }

    @Override
    public String toString() {
        return "ExampleRequest[" +
                "name=" + name + ", " +
                "amount=" + amount + ']';
    }

}
