package ru.kataev.birds;

public class Parrot extends Bird {
    private final String text;

    public Parrot(String text) {
        super("Попугай");
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException("Текст попугая не может быть пустым!");
        }
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public void sing() {
        int length = randomBetween(1, text.length());
        System.out.println(text.substring(0, length));
    }

    @Override
    public String toString() {
        return getSpecies() + " (текст: " + text + ")";
    }
}