package ru.kataev.birds;

public class Cuckoo extends Bird {

    public Cuckoo() {
        super("Кукушка");
    }

    @Override
    public void sing() {
        int count = randomBetween(1, 10);
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < count; i++) {
            if (i > 0) {
                result.append(" ");
            }
            result.append("ку-ку");
        }

        System.out.println(result);
    }
}