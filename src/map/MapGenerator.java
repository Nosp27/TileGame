package map;

import heroes.Hero;
import heroes.HeroFactory;
import javafx.util.Pair;
import map.locations.Location;
import map.locations.LocationFactory;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

public class MapGenerator {
    int size;
    private Location[][] map;
    private List<Hero> heroes;

    public MapGenerator() {
        this.heroes = new LinkedList<>();
    }

    public void generateMap(int size) {
        //TODO: map generator
        this.size = size;
        map = new Location[size][size];
        int towerCoord = size / 2;
        map[towerCoord][towerCoord] = LocationFactory.getTower();

        for (int i = 0; i < size; ++i)
            for (int j = 0; j < size; ++j) {
                if (map[i][j] == null) {
                    map[i][j] = LocationFactory.getRandomLocation();
                }
            }
    }

    public Hero addHero() {
        int towerCoord = size / 2;
        Hero sorcer = HeroFactory.sorcerer(this);
        HeroFactory.place(sorcer, new int[]{towerCoord, towerCoord});
        heroes.add(sorcer);
        return sorcer;
    }

    public Location[][] getMap() {
        return map;
    }

    public List<Hero> getHeroes() {
        return heroes;
    }

    public Stack<Integer[]> calculateRoute(Hero hero) {
        System.out.println("calculating route (" + Thread.currentThread() + ")");
        synchronized (hero){
            System.out.println("calculating route2");
            Stack<Integer[]> route = new Stack<>();

            int startX = hero.getX();
            int startY = hero.getY();
            int targetX;
            int targetY;

            int towerCoord = size / 2;
            switch (hero.getMission()) {
                case "seek":
                    targetX = ThreadLocalRandom.current().nextInt(size);
                    targetY = ThreadLocalRandom.current().nextInt(size);
                    break;
                case "return":
                    targetX = towerCoord;
                    targetY = towerCoord;
                    break;
                default:
                    targetX = 0;
                    targetY = 0;
            }

            int _x = startX;
            int _y = startY;

            while (_x != targetX || _y != targetY) {
                List<Point> variants = getNearby(_x, _y);

                if (_x <= targetX)
                    variants.remove(new Point(_x - 1, _y));
                if (_x >= targetX)
                    variants.remove(new Point(_x + 1, _y));
                if (_y <= targetY)
                    variants.remove(new Point(_x, _y - 1));
                if (_y >= targetY)
                    variants.remove(new Point(_x, _y + 1));

                Stream<Pair<Location, Integer[]>> locations = variants.stream().map(p -> new Pair<>(map[p.y][p.x], new Integer[]{p.y, p.x}));
                Integer[] _coords = hero.preferLocation(locations);
                _y = _coords[0];
                _x = _coords[1];

                route.push(new Integer[]{_y, _x});
            }
            Stack<Integer[]> newRoute = new Stack<>();
            while (!route.empty())
                newRoute.push(route.pop());

            return newRoute;
        }
    }

    public List<Location> getNearbyLocations(int x, int y){
        List<Location> result = new LinkedList<>();
        for(Point p : getNearby(x, y))
            result.add(map[p.y][p.x]);
        return result;
    }

    private List<Point> getNearby(int x, int y) {
        List<Point> result = new LinkedList<>();
        result.add(new Point(x, y - 1));
        result.add(new Point(x, y + 1));
        result.add(new Point(x - 1, y));
        result.add(new Point(x + 1, y));
        result.removeIf(c -> !cellExists(c.x, c.y));
        return result;
    }

    private boolean cellExists(Integer x, Integer y) {
        if (x >= size || x < 0) return false;
        if (y >= size || y < 0) return false;
        return true;
    }
}
