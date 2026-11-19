package ru.kataev.main;

import ru.kataev.birds.Bird;
import ru.kataev.birds.Cuckoo;
import ru.kataev.birds.Parrot;
import ru.kataev.birds.Sparrow;
import ru.kataev.cities.City;
import ru.kataev.cities.Road;
import ru.kataev.cities.Route;
import ru.kataev.cities.TwoWayCity;
import ru.kataev.geometry.Point;
import ru.kataev.geometry.Point3D;
import ru.kataev.math.Power;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private final List<City> cities = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        new Main().run(args);
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите целое число.");
            }
        }
    }

    private int readNonNegative(String prompt) {
        int value = readInt(prompt);
        while (value < 0) {
            System.out.println("Значение не может быть отрицательным.");
            value = readInt(prompt);
        }
        return value;
    }

    private int readIndex(String prompt, int max) {
        int value = readInt(prompt);
        while (value < 1 || value > max) {
            System.out.println("Введите число от 1 до " + max + ".");
            value = readInt(prompt);
        }
        return value;
    }

    private String readNonEmpty(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Значение не может быть пустым.");
        }
    }

    private City findCity(String name) {
        for (City city : cities) {
            if (city.getName().equalsIgnoreCase(name)) {
                return city;
            }
        }
        return null;
    }

    private void printNumberedCities() {
        for (int i = 0; i < cities.size(); i++) {
            System.out.println((i + 1) + ". " + cities.get(i).getName());
        }
    }

    private boolean containsRoadTo(List<Road> list, City target) {
        for (Road road : list) {
            if (road.getCity() == target) {
                return true;
            }
        }
        return false;
    }

    private void run(String[] args) {
        while (true) {
            System.out.println("\n========== ГЛАВНОЕ МЕНЮ ==========");
            System.out.println("(1) - 1.10 Дороги");
            System.out.println("(2) - 2.5 Маршрут");
            System.out.println("(3) - 3.3 Двусторонняя дорога");
            System.out.println("(4) - 3.5 Трёхмерная точка");
            System.out.println("(5) - 4.3 Птицы");
            System.out.println("(6) - 5.9 Городим");
            System.out.println("(7) - 6.5 Сравнение городов");
            System.out.println("(8) - 7.3 Возведение в степень");
            System.out.println("(9) - 8.4 Клонирование точки");
            System.out.println("(0) - Выход");

            int choice = readInt("Выберите номер 0 - 9: ");

            switch (choice) {
                case 1:
                    citiesMenu();
                    break;
                case 2:
                    routeDemo();
                    break;
                case 3:
                    twoWayDemo();
                    break;
                case 4:
                    point3dDemo();
                    break;
                case 5:
                    birdsDemo();
                    break;
                case 6:
                    graphDemo();
                    break;
                case 7:
                    equalsDemo();
                    break;
                case 8:
                    powerDemo(args);
                    break;
                case 9:
                    cloneDemo();
                    break;
                case 0:
                    System.out.println("Программа завершена.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Выберите номер от 0 до 9.");
            }
        }
    }

    // 1.10
    private void citiesMenu() {
        int choice;

        do {
            System.out.println("\n=== УПРАВЛЕНИЕ ГОРОДАМИ И ДОРОГАМИ ===");
            System.out.println("1. Создать город");
            System.out.println("2. Создать город с дорогами");
            System.out.println("3. Добавить дорогу");
            System.out.println("4. Удалить дорогу");
            System.out.println("5. Показать все города");
            System.out.println("0. Вернуться в главное меню");

            choice = readInt("Выберите действие: ");

            switch (choice) {
                case 1:
                    createCity();
                    break;
                case 2:
                    createCityWithRoads();
                    break;
                case 3:
                    addRoad();
                    break;
                case 4:
                    removeRoad();
                    break;
                case 5:
                    showCities();
                    break;
                case 0:
                    System.out.println("Возвращаемся в главное меню...");
                    break;
                default:
                    System.out.println("Такого пункта нет!");
            }
        } while (choice != 0);
    }

    private void createCity() {
        String name = readNonEmpty("Введите название города: ");

        if (findCity(name) != null) {
            System.out.println("Такой город уже существует!");
            return;
        }

        cities.add(new City(name));
        System.out.println("Город создан!");
    }

    private void createCityWithRoads() {
        String name = readNonEmpty("Введите название города: ");

        if (findCity(name) != null) {
            System.out.println("Такой город уже существует!");
            return;
        }

        List<Road> newRoads = new ArrayList<>();

        if (!cities.isEmpty()) {
            int count = readNonNegative("Сколько дорог создать: ");
            while (count > cities.size()) {
                System.out.println("Дорог не может быть больше, чем городов (" + cities.size() + ").");
                count = readNonNegative("Сколько дорог создать: ");
            }

            while (newRoads.size() < count) {
                System.out.println("\nДоступные города:");
                printNumberedCities();

                int number = readIndex("Выберите город: ", cities.size());
                City target = cities.get(number - 1);

                if (containsRoadTo(newRoads, target)) {
                    System.out.println("Дорога в этот город уже добавлена!");
                    continue;
                }

                int cost = readNonNegative("Стоимость дороги: ");
                newRoads.add(new Road(target, cost));
            }
        }

        cities.add(new City(name, newRoads));
        System.out.println("Город с дорогами создан!");
    }

    private void addRoad() {
        if (cities.size() < 2) {
            System.out.println("Нужно создать минимум два города!");
            return;
        }

        printNumberedCities();

        int from = readIndex("Откуда строим дорогу: ", cities.size());
        int to = readIndex("Куда строим дорогу: ", cities.size());

        if (from == to) {
            System.out.println("Нельзя построить дорогу в тот же город!");
            return;
        }

        if (cities.get(from - 1).hasRoadTo(cities.get(to - 1))) {
            System.out.println("Такая дорога уже существует!");
            return;
        }

        int cost = readNonNegative("Стоимость дороги: ");

        cities.get(from - 1).addRoad(cities.get(to - 1), cost);
        System.out.println("Дорога добавлена!");
    }

    private void removeRoad() {
        if (cities.size() < 2) {
            System.out.println("Недостаточно городов!");
            return;
        }

        printNumberedCities();

        int from = readIndex("Из какого города удалить дорогу: ", cities.size());
        int to = readIndex("В какой город ведёт удаляемая дорога: ", cities.size());

        if (cities.get(from - 1).removeRoad(cities.get(to - 1))) {
            System.out.println("Дорога удалена!");
        } else {
            System.out.println("Такой дороги нет!");
        }
    }

    private void showCities() {
        if (cities.isEmpty()) {
            System.out.println("Городов пока нет!");
            return;
        }

        for (City city : cities) {
            System.out.println(city);
        }
    }

    // 2.5
    private void routeDemo() {
        System.out.println("--- 2.5 Маршрут ---");

        City a = new City("A");
        City b = new City("B");
        City c = new City("C");
        City d = new City("D");
        City e = new City("E");
        City f = new City("F");

        a.addRoad(b, 5);
        b.addRoad(a, 5);
        a.addRoad(f, 1);
        f.addRoad(b, 1);
        f.addRoad(e, 2);
        e.addRoad(f, 2);
        b.addRoad(c, 3);
        c.addRoad(b, 3);
        c.addRoad(d, 4);
        d.addRoad(c, 4);
        d.addRoad(e, 2);
        a.addRoad(d, 6);
        d.addRoad(a, 6);

        Route route = new Route(f, d);

        System.out.println("Маршрут из F в D:");
        System.out.println(route);
        System.out.println("Количество городов в маршруте: " + route.getRoute().length);
    }

    // 3.3
    private void twoWayDemo() {
        System.out.println("--- 3.3 Двусторонняя дорога ---");

        String nameA = readNonEmpty("Название первого города: ");
        String nameB = readNonEmpty("Название второго города: ");

        TwoWayCity a = new TwoWayCity(nameA);
        TwoWayCity b = new TwoWayCity(nameB);

        int cost = readNonNegative("Стоимость дороги: ");

        a.addRoad(b, cost);
        System.out.println("\nДобавили дорогу один раз, из " + a.getName() + " в " + b.getName() + ":");
        System.out.println(a);
        System.out.println(b);

        a.removeRoad(b);
        System.out.println("\nУдалили дорогу из " + a.getName() + " в " + b.getName() + ":");
        System.out.println(a);
        System.out.println(b);
    }

    // 3.5
    private void point3dDemo() {
        System.out.println("--- 3.5 Трёхмерная точка ---");

        int x = readInt("x: ");
        int y = readInt("y: ");
        int z = readInt("z: ");

        Point3D point = new Point3D(x, y, z);
        System.out.println("Трёхмерная точка: " + point);
    }

    // 4.3
    private void birdsDemo() {
        System.out.println("--- 4.3 Птицы ---");

        String text = readNonEmpty("Введите текст для попугая: ");

        Bird[] birds = {new Sparrow(), new Cuckoo(), new Parrot(text)};

        for (Bird bird : birds) {
            System.out.print(bird.getSpecies() + ": ");
            bird.sing();
        }
    }

    // 5.9
    private void graphDemo() {
        System.out.println("--- 5.9 Городим ---");

        int cost = readNonNegative("Стоимость каждой дороги: ");
        RoadMap map = new RoadMap(cost);

        System.out.println();
        System.out.println(map);
    }

    // 6.5
    private void equalsDemo() {
        System.out.println("--- 6.5 Сравнение городов ---");

        int cost = readNonNegative("Стоимость дороги: ");

        City hub = new City("Центр");
        City x = new City("X");
        TwoWayCity y = new TwoWayCity("Y");
        City z = new City("Z");
        City empty1 = new City("Пустой1");
        City empty2 = new City("Пустой2");

        x.addRoad(hub, cost);
        y.addRoad(hub, cost);
        z.addRoad(hub, cost + 1);

        System.out.println("X равен Y (одинаковые дороги, разные типы)? " + x.equals(y));
        System.out.println("Y равен X? " + y.equals(x));
        System.out.println("X равен Z (другая стоимость)? " + x.equals(z));
        System.out.println("Пустой1 равен Пустой2 (дорог нет)? " + empty1.equals(empty2));
        System.out.println("Пустой1 и Пустой2 один и тот же объект? " + (empty1 == empty2));
        System.out.println("X равен Пустой1? " + x.equals(empty1));
    }

    // 7.3
    private void powerDemo(String[] args) {
        System.out.println("--- 7.3 Возведение в степень ---");

        Power power = new Power();

        if (args.length >= 2) {
            try {
                System.out.println("Параметры командной строки: " + args[0] + " и " + args[1]);
                System.out.println(args[0] + " ^ " + args[1] + " = " + power.power(args[0], args[1]));
            } catch (NumberFormatException e) {
                System.out.println("Параметры командной строки не являются целыми числами.");
            }
        } else {
            System.out.println("Программа запущена без параметров командной строки.");
            System.out.println("(Задаются в настройках запуска: Program arguments, например: 2 10)");
        }

        while (true) {
            String x = readNonEmpty("Введите X: ");
            String y = readNonEmpty("Введите Y: ");

            try {
                System.out.println(x + " ^ " + y + " = " + power.power(x, y));
                return;
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите целые числа.");
            }
        }
    }

    // 8.4
    private void cloneDemo() {
        System.out.println("--- 8.4 Клонирование точки ---");

        int x = readInt("x: ");
        int y = readInt("y: ");

        Point original = new Point(x, y);
        Point copy = original.clone();

        System.out.println("Оригинал: " + original);
        System.out.println("Копия: " + copy);

        original.setX(original.getX() + 100);

        System.out.println("\nПосле изменения оригинала:");
        System.out.println("Оригинал: " + original);
        System.out.println("Копия: " + copy);
        System.out.println("Это один и тот же объект? " + (original == copy));
    }
}