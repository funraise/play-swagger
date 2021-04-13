package com.example.dto;

import java.util.Objects;

public final class ExampleResponse {
    private final Long id;
    private final String name;
    private final int amount;

    public ExampleResponse(Long id, String name, int amount) {
        this.id = id;
        this.name = name;
        this.amount = amount;
    }

    public Long getId() {
        return id;
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
        var that = (ExampleResponse) obj;
        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.name, that.name) &&
                this.amount == that.amount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, amount);
    }

    @Override
    public String toString() {
        return "ExampleResponse[" +
                "id=" + id + ", " +
                "name=" + name + ", " +
                "amount=" + amount + ']';
    }

}
