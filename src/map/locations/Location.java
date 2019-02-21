package map.locations;

import heroes.Hero;
import javafx.util.Pair;
import mechanics.quests.MonsterQuest;
import monsters.Monster;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Location {
    static Random r = new Random();
    private String pathName;

    //for location data
    List<Monster> monsters;
    //

    int monsterFactor;
    int treasureFactor;
    int encounterFactor;

    public String getPathName() {
        return pathName;
    }

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
        monsters = new LinkedList<>();
    }

    //get event for hero
    public Pair<EventType, Object> message_heroCame(Hero h){
        switch (h.currentState()){
            case WALKING:
                if(check(monsterFactor) && monsters.size() > 0) //monster attacked!
                    return new Pair<>(EventType.MONSTER, getRandomMonster());
                else if(check(treasureFactor) && false)//dummy
                    return new Pair<>(EventType.TREASURE, null);//TODO: finish treasures
                else if(check(encounterFactor) && false)//dummy
                    return new Pair<>(EventType.ENCOUNTER, null);//TODO: finish encounters
                else return new Pair<>(EventType.NONE, null);
                default: return new Pair<>(EventType.NONE, null);
        }
    }

    private Monster getRandomMonster() {
        if(monsters.size() == 0)
            return null;

        return monsters.get(ThreadLocalRandom.current().nextInt(monsters.size()));
    }

    @Override
    public String toString(){
        return pathName;
    }

    private boolean check(int parameter){
        return parameter <= ThreadLocalRandom.current().nextInt(100);
    }

    public void setQuestMonsters(MonsterQuest q) {
        this.monsters.clear();
        this.monsters.addAll(q.getMonsters());
    }
}
