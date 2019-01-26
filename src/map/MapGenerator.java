package map;

import heroes.Hero;
import heroes.HeroFactory;
import map.locations.Location;
import map.locations.LocationFabric;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

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
        addHero();
    }

    void addHero() {
        int towerCoord = size / 2;
        Hero sorcer = HeroFactory.sorcerer();
        HeroFactory.place(sorcer, new int[]{towerCoord, towerCoord});
        heroes.add(sorcer);
    }

    public Location[][] getMap() {
        return map;
    }

    public List<Hero> getHeroes() {
        return heroes;
    }

    public Stack<Location> calculateRoute(Hero hero) {
        //TODO: route calculation
        throw new NotImplementedException();
    }
}
