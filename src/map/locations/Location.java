package map.locations;

import heroes.Hero;
import javafx.util.Pair;
import monsters.Monster;
import monsters.MonsterListener;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Location implements MonsterListener {
    static Random r = new Random();
    private String pathName;

    String name;

    public String getName() {
        if (discovered)
            return name;
        else return "Uncovered Location";
    }

    //for location data
    private List<Monster> monsters;

    //
    void addMonster(Monster m) {
        monsters.add(m);
        m.addListener(this);
    }

    List<LocationListener> listeners = new LinkedList<>();

    public void addLocationListener(LocationListener listener) {
        listeners.add(listener);
    }

    int monsterFactor;
    int treasureFactor;
    int encounterFactor;

    private boolean discovered;

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
        discovered = false;
    }

    //get event for hero
    public Pair<EventType, Object> message_heroCame(Hero h) {
        if (h.getHeroState() == h.getWalkingState()) {
            if (check(monsterFactor) && monsters.size() > 0) //monster attacked!
                return new Pair<>(EventType.MONSTER, getRandomMonster());
            else if (check(treasureFactor) && false)//dummy
                return new Pair<>(EventType.TREASURE, null);//TODO: finish treasures
            else if (check(encounterFactor) && false)//dummy
                return new Pair<>(EventType.ENCOUNTER, null);//TODO: finish encounters
            else return new Pair<>(EventType.NONE, null);
        } else
            return new Pair<>(EventType.NONE, null);
    }

    public void discover() {
        if (discovered)
            return;

        discovered = true;

        for (LocationListener l : listeners)
            l.onDiscover(this);
    }

    public boolean isDiscovered() {
        return discovered;
    }

    private Monster getRandomMonster() {
        if (monsters.size() == 0)
            return null;

        Monster randomMonster = monsters.get(ThreadLocalRandom.current().nextInt(monsters.size()));

        for (LocationListener l : listeners)
            l.onMonsterGot(this, randomMonster);

        return randomMonster;
    }

    @Override
    public String toString() {
        return pathName;
    }

    private boolean check(int parameter) {
        return parameter <= ThreadLocalRandom.current().nextInt(100);
    }

    @Override
    public void onKill(Monster m) {
        monsters.remove(m);
    }
}
