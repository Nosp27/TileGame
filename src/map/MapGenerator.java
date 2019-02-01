package map;

import heroes.Hero;
import heroes.HeroFactory;
import javafx.util.Pair;
import map.locations.Location;
import map.locations.LocationFabric;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

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
        map[towerCoord][towerCoord] = LocationFabric.getTower();

        for (int i = 0; i < size; ++i)
            for (int j = 0; j < size; ++j) {
                if (map[i][j] == null) {
                    map[i][j] = LocationFabric.getRandomLocation();
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

    public Stack<Location> calculateRoute(Hero hero) {
        Stack<Location> route = new Stack<>();

        int startX = hero.getX();
        int startY = hero.getY();
        int targetX = ThreadLocalRandom.current().nextInt(size);
        int targetY = ThreadLocalRandom.current().nextInt(size);

        int _x = startX;
        int _y = startY;
        Location prev = null;

        while(_x != targetX || _y != targetY){
            Map<Location, Pair<Integer, Integer>> variants = getNearby(_x, _y);
            if(prev != null)
                variants.remove(prev);

            if(_x <= targetX)
                variants.remove(map[_y][_x-1]);
            if(_x >= targetX)
                variants.remove(map[_y][_x+1]);
            if(_y <= targetY)
                variants.remove(map[_y-1][_x]);
            if(_y >= targetY)
                variants.remove(map[_y+1][_x]);

            prev = map[_x][_y];
            Location desirable = hero.preferLocation(variants.keySet());
            Pair<Integer, Integer> _coords = variants.get(desirable);
            _y = _coords.getKey();
            _x = _coords.getValue();

            route.push(desirable);
        }
        return route;
    }

    private Map<Location, Pair<Integer, Integer>> getNearby(int x, int y){
        HashMap<Location, Pair<Integer, Integer>> result = new HashMap<>();
        try{result.put(map[y][x-1], new Pair<>(y,x-1)); } catch(IndexOutOfBoundsException ignore){}
        try{result.put(map[y][x+1], new Pair<>(y,x+1));}catch(IndexOutOfBoundsException ignore){}
        try{result.put(map[y-1][x], new Pair<>(y-1,x));}catch(IndexOutOfBoundsException ignore){}
        try{result.put(map[y+1][x], new Pair<>(y+1,x));}catch(IndexOutOfBoundsException ignore){}

        return result;
    }
}
