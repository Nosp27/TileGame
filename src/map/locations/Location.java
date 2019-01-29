package map.locations;

import heroes.Hero;
import javafx.util.Pair;
import monsters.Monster;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Location {
    static Random r = new Random();
    String pathName;

    //for sprites
    List<String> paths;

    //for location data
    List<Monster> monsters;
    //

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

    //get event for hero
    public Pair<EventType, Object> message_heroCame(Hero h){
        switch (h.currentState()){
            case WALKING:
                if(check(monsterFactor)) //monster attacked!
                    return new Pair<>(EventType.MONSTER, getRandomMonster());
                else if(check(treasureFactor))
                    return new Pair<>(EventType.TREASURE, null);//TODO: finish treasures
                else if(check(encounterFactor))
                    return new Pair<>(EventType.ENCOUNTER, null);//TODO: finish encounters
                else return new Pair<>(EventType.NONE, null);
        }
        return null;
    }

    private Monster getRandomMonster() {
        return monsters.get(ThreadLocalRandom.current().nextInt(monsters.size()));
    }

    @Override
    public String toString(){
        return pathName;
    }

    private boolean check(int parameter){
        return parameter <= ThreadLocalRandom.current().nextInt(100);
    }
}
