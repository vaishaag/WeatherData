package com.app.weather.model;

public enum Condition {

    RAIN("Rain"), SNOW("Snow"), SUNNY("Sunny");

    private String value;

    Condition(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}

