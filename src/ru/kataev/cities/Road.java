package ru.kataev.cities;

// Дорога: город назначения и стоимость.
// Нужна в City вместо Map: из-за equals в задаче 6.5 города нельзя
// использовать как ключи словаря.
public class Road {
    private final City city;
    private final int cost;

    public Road(City city, int cost) {
        if (city == null) {
            throw new IllegalArgumentException("Город назначения не может быть null!");
        }
        if (cost < 0) {
            throw new IllegalArgumentException("Стоимость не может быть отрицательной!");
        }
        this.city = city;
        this.cost = cost;
    }

    public City getCity() {
        return city;
    }

    public int getCost() {
        return cost;
    }

    // Задача 6.5: дороги равны, если ведут в один и тот же город по одной цене.
    // Города сравниваются по ссылке, чтобы не вызывать City.equals.
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Road)) {
            return false;
        }
        Road other = (Road) o;
        return city == other.city && cost == other.cost;
    }

    @Override
    public int hashCode() {
        return 31 * System.identityHashCode(city) + cost;
    }

    @Override
    public String toString() {
        return city.getName() + ":" + cost;
    }
}