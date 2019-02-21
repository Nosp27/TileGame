package map;

import heroes.Hero;
import heroes.HeroFactory;
import javafx.util.Pair;
import map.locations.Location;
import map.locations.LocationFabric;
import mechanics.Logger;

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
        this.size = size;
        map = new Location[size][size];

        generateRandomTileSet();
        createClusters(3, 6, 4);
        createTower();
    }

    private void createTower(){
        int towerCoord = size / 2;
        map[towerCoord][towerCoord] = LocationFabric.getTower();
    }

    private void generateRandomTileSet(){
        for (int i = 0; i < size; ++i)
            for (int j = 0; j < size; ++j) {
                if (map[i][j] == null) {
                    map[i][j] = LocationFabric.getRandomLocation();
                }
            }
    }

    private void createClusters(int n, int _size, int deviation){
        for(int i = 0; i < n; i++){
            Location l = LocationFabric.getPlain();

            int realSize = ThreadLocalRandom.current().nextInt(-deviation/2, deviation/2) + _size;
            int row = ThreadLocalRandom.current().nextInt(size);
            int col = ThreadLocalRandom.current().nextInt(size);

            if(realSize <= 0)
                throw new IllegalArgumentException();

            class _iterationCompleter{
                void iteration(int remained, int row, int col){
                    if(remained == 0)
                        return;

                    if(row < 0 || col < 0 || row >= size || col >= size)
                        return;

                    if(ThreadLocalRandom.current().nextInt(10) < 1)
                        return;

                    map[row][col] = l;

                    iteration(remained-1, row-1, col);
                    iteration(remained-1, row+1, col);
                    iteration(remained-1, row, col-1);
                    iteration(remained-1, row, col+1);
                }
            }

            new _iterationCompleter().iteration(realSize, row, col);
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
        synchronized (hero){
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
