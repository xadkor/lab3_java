package ru.kataev.birds;

import java.util.Random;

public abstract class Bird {
    private final String species;
    private final Random random = new Random();

    public Bird(String species) {
        this.species = species;
    }

    public String getSpecies() {
        return species;
    }

    public abstract void sing();

    protected int randomBetween(int min, int max) {
        return min + random.nextInt(max - min + 1);
    }

    @Override
    public String toString() {
        return species;
    }
}