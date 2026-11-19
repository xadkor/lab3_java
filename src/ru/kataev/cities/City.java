package ru.kataev.cities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class City {
    private final String name;
    private final List<Road> roads = new ArrayList<>();

    public City(String name) {
        this(name, null);
    }

    // Создание города с заранее заданными путями
    public City(String name, List<Road> roads) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Название города не может быть пустым!");
        }
        this.name = name.trim();

        if (roads != null) {
            for (Road road : roads) {
                if (!addRoad(road.getCity(), road.getCost())) {
                    throw new IllegalArgumentException("Некорректная дорога в списке путей!");
                }
            }
        }
    }

    public String getName() {
        return name;
    }

    public List<Road> getRoads() {
        return Collections.unmodifiableList(roads);
    }

    public boolean addRoad(City city, int cost) {
        if (city == null || city == this || cost < 0 || hasRoadTo(city)) {
            return false;
        }
        roads.add(new Road(city, cost));
        return true;
    }

    public boolean removeRoad(City city) {
        return roads.removeIf(road -> road.getCity() == city);
    }

    public boolean hasRoadTo(City city) {
        for (Road road : roads) {
            if (road.getCity() == city) {
                return true;
            }
        }
        return false;
    }

    // Задача 6.5: города равны, если у них одинаковый набор дорог.
    // instanceof (а не getClass) позволяет сравнивать City и TwoWayCity.
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof City)) {
            return false;
        }
        City other = (City) o;
        return roads.size() == other.roads.size() && roads.containsAll(other.roads);
    }

    // Задача 6.5: хеш считается по тем же данным, что и equals (порядок дорог не важен)
    @Override
    public int hashCode() {
        return new HashSet<>(roads).hashCode();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Город: " + name);

        if (roads.isEmpty()) {
            result.append("\nДорог нет");
        } else {
            result.append("\nДороги:");
            for (Road road : roads) {
                result.append("\n -> ")
                        .append(road.getCity().getName())
                        .append(", стоимость: ")
                        .append(road.getCost());
            }
        }

        return result.toString();
    }
}