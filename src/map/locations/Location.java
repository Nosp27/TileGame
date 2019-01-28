package map.locations;

import heroes.Hero;
import heroes.HeroAutomat;
import heroes.State;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static heroes.State.FIGHT_ENGAGE;

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

    public void message_heroCame(Hero h){
        switch (h.currentState()){
            case WALKING:
                if(ThreadLocalRandom.current().nextInt(100) < treasureFactor) //monster attacked!
                {

                }
                break;
        }
    }

    @Override
    public String toString(){
        return pathName;
    }
}
