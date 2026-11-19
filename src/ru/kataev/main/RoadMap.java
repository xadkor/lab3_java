package ru.kataev.main;

import ru.kataev.cities.City;
import ru.kataev.cities.TwoWayCity;

public class RoadMap {
    private final City[] cities;

    public RoadMap(int cost) {
        if (cost < 0) {
            throw new IllegalArgumentException("Стоимость не может быть отрицательной!");
        }

        TwoWayCity a = new TwoWayCity("A");
        City b = new City("B");
        City c = new City("C");
        TwoWayCity d = new TwoWayCity("D");
        City e = new City("E");

        a.addRoad(b, cost); // A <-> B
        a.addRoad(c, cost); // A <-> C
        b.addRoad(c, cost); // B -> C
        d.addRoad(b, cost); // D <-> B
        d.addRoad(e, cost); // D <-> E
        e.addRoad(c, cost); // E -> C

        cities = new City[]{a, b, c, d, e};
    }

    public City[] getCities() {
        return cities.clone();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < cities.length; i++) {
            if (i > 0) {
                result.append("\n\n");
            }
            result.append(cities[i]);
        }

        return result.toString();
    }
}