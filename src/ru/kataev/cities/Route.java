package ru.kataev.cities;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class Route {
    private City start;
    private City end;

    public Route(City start, City end) {
        setStart(start);
        setEnd(end);
    }

    public void setStart(City start) {
        if (start == null) {
            throw new IllegalArgumentException("Начальный город не может быть null!");
        }

        this.start = start;
    }

    public void setEnd(City end) {
        if (end == null) {
            throw new IllegalArgumentException("Конечный город не может быть null!");
        }

        this.end = end;
    }

    public City getStart() {
        return start;
    }

    public City getEnd() {
        return end;
    }

    public City[] getRoute() {
        Queue<City> queue = new ArrayDeque<>();
        Set<City> visited = Collections.newSetFromMap(new IdentityHashMap<>());
        Map<City, City> previous = new IdentityHashMap<>();

        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            City current = queue.poll();

            if (current == end) {
                break;
            }

            for (Road road : current.getRoads()) {
                City next = road.getCity();
                if (!visited.contains(next)) {
                    visited.add(next);
                    previous.put(next, current);
                    queue.add(next);
                }
            }
        }

        if (!visited.contains(end)) {
            return new City[0];
        }

        ArrayList<City> path = new ArrayList<>();

        City current = end;

        while (current != null) {
            path.add(current);
            current = previous.get(current);
        }

        Collections.reverse(path);

        return path.toArray(new City[0]);
    }

    @Override
    public String toString() {
        City[] route = getRoute();

        if (route.length == 0) {
            return "Маршрут не найден";
        }

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < route.length; i++) {
            result.append(route[i].getName());

            if (i < route.length - 1) {
                result.append(" -> ");
            }
        }

        return result.toString();
    }
}