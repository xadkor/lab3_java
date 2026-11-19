package ru.kataev.cities;

public class TwoWayCity extends City {

    public TwoWayCity(String name) {
        super(name);
    }

    @Override
    public boolean addRoad(City city, int cost) {
        if (!super.addRoad(city, cost)) {
            return false;
        }
        // проверка защищает от бесконечной рекурсии между двумя TwoWayCity
        if (!city.hasRoadTo(this)) {
            city.addRoad(this, cost);
        }
        return true;
    }

    @Override
    public boolean removeRoad(City city) {
        if (!super.removeRoad(city)) {
            return false;
        }
        city.removeRoad(this);
        return true;
    }
}