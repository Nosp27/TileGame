package heroes;

import javafx.util.Pair;
import map.locations.EventType;
import map.locations.Location;
import mechanics.fight.MonsterFight;
import monsters.Monster;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Hero {
    int x, y;
    String pathName;

    int basePower;
    float retreatBonus;
    float dreadModifier;

    private final HeroAutomat heroAutomat = HeroAutomat.generateAutomat();

    Location currentLocation;

    List<Buff> buffs;

    MonsterFight monsterFight;

    //accessors
    public State currentState() {
        return heroAutomat.getCurrentState();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getPathName() {
        return pathName;
    }

    public float getRetreatBonus() {
        //TODO: add buffs
        return retreatBonus;
    }

    public float getDreadModifier() {
        //TODO: add buffs
        return dreadModifier;
    }

    public Integer getSin(MortalSins sinType) {
        return sins.getOrDefault(sinType, 0);
    }

    public Integer setSin(MortalSins sinType, Integer value) {
        if (value < 0 || value > 100)
            throw new IllegalArgumentException();
        return sins.put(sinType, value);
    }
    //

    //mortal sins
    public enum MortalSins {
        ANGER,
        ENVY,
        PRIDE,
        AVARICE,//алчность
        LUST,
        GLUTTONY
    }

    private Map<MortalSins, Integer> sins;
    //

    //personal qualities
    //TODO: personal qualities
    //


    //constructor
    public Hero() {
        sins = new HashMap<>(6);
    }

    //public methods
    public final Location preferLocation(List<Location> variants) {
        Float bestChoiceCoeff = 0f;
        Location bestChoice = null;
        Map<Location, Float> map = calculatePreferredLocation(variants);
        for (Location l : map.keySet()) {
            if (bestChoice == null || map.get(l) > bestChoiceCoeff) {
                bestChoice = l;
                bestChoiceCoeff = map.get(l);
            }
        }
        return bestChoice;
    }

    public int getStrength() {
        //TODO: implement strength count
        throw new NotImplementedException();
    }

    //protected
    //return map for
    protected Map<Location, Float> calculatePreferredLocation(List<Location> variants) {
        //TODO: implement preferences
        throw new NotImplementedException();
    }


    //following methods affect behaviour
    private void doSomething() {
        Pair<EventType, Object> event;
        switch (heroAutomat.getCurrentState()) {
            case IDLE:
                //TODO: idle work
                break;
            case WALKING:
                //TODO: walking
                event = currentLocation.message_heroCame(this);
                switch (event.getKey()) {
                    case MONSTER:
                        startFight((Monster) event.getValue());
                }
                break;
            case SEARCHING:
                break;
            case FIGHT_ENGAGE:
                break;
            case FIGHT:
                break;
            case RETREAT:
                break;
            case RETURNING:
                break;
        }
    }

    private void startFight(Monster monster) {
        heroAutomat.transit(State.FIGHT_ENGAGE);
        monsterFight = new MonsterFight(this, monster, this::wound, this::collectPrize);
    }

    private void retreat(){

    }

    private void wound() {
        //TODO: process wound
        heroAutomat.transit(State.RETURNING);
    }

    private void collectPrize() {
        //TODO: boost hero
        heroAutomat.transitBack();
    }
    //
}
