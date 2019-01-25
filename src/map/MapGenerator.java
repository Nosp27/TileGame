package map;

import heroes.Hero;
import map.locations.Location;
import map.locations.LocationFabric;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Stack;

public class MapGenerator {
    int size;
    private Location[][] map;

    public void generateMap(int size){
        //TODO: map generator
        map = new Location[size][size];
        int towerCoord = size / 2;
        map[towerCoord][towerCoord] = LocationFabric.getTower();

        for(int i = 0; i < size; ++i)
            for(int j = 0; j < size; ++j)
            {
                if(map[i][j] == null){
                    map[i][j] = LocationFabric.getRandomLocation();
                }
            }
    }

    public Location[][] getMap(){
        return map;
    }

    public Stack<Location> calculateRoute(Hero hero){
        //TODO: route calculation
        throw new NotImplementedException();
    }
}
