package map.locations;

import java.util.LinkedList;
import java.util.List;

public class Location {

    String name;

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

    Location(String name) {
        this.name = name;
        paths = new LinkedList<>();
    }
}
