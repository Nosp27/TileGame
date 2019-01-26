package map.locations;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Location {
    static Random r = new Random();
    String pathName;

    //for sprites
    List<String> paths;

    int monsterFactor;
    int treasureFactor;
    int encounterFactor;

    public int getMonsterFactor() {
        return monsterFactor;
    }

    public int getEncounterFactor() {
        return encounterFactor;
    }

    public int getTreasureFactor() {
        return treasureFactor;
    }

    Location(String pathName) {
        this.pathName = pathName;
        paths = new LinkedList<>();
    }

    public String getRandomTile(){
        if(paths.size() == 0)
            return null;
        return paths.get(r.nextInt(paths.size()));
    }

    @Override
    public String toString(){
        return pathName;
    }
}
