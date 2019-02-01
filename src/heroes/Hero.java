package heroes;

import javafx.util.Pair;
import map.MapGenerator;
import map.locations.EventType;
import map.locations.Location;
import mechanics.fight.FightState;
import mechanics.fight.MonsterFight;
import monsters.Monster;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Hero {
    int x, y;
    String pathName;

    private MapGenerator mg;

    int basePower;
    float retreatBonus;
    float dreadModifier;

    //hero control
    private String command;
    private LinkedList<String> parameters;
    Stack<Location> route;
    //

    private final HeroAutomat heroAutomat = HeroAutomat.generateAutomat();

    Location currentLocation;

    List<Buff> buffs;

    MonsterFight monsterFight;

    //constructor
    public Hero(MapGenerator mg) {
        this.mg = mg;
        sins = new HashMap<>(6);
        parameters = new LinkedList<>();
        buffs = new LinkedList<>();
    }

    //region accessors
    public Location getCurrentLocation() {
        return currentLocation;
    }

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
    //endregion

    public int getStrength() {
        //TODO: implement strength count
        throw new NotImplementedException();
    }

    //region mortal sins
    public enum MortalSins {
        ANGER,
        ENVY,
        PRIDE,
        AVARICE,//алчность
        LUST,
        GLUTTONY
    }

    private Map<MortalSins, Integer> sins;
    //endregion

    //personal qualities
    //TODO: personal qualities
    //

    //public methods
    public final Location preferLocation(Collection<Location> variants) {
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

    public void giveOrder(String order) {
//        if(heroAutomat.getCurrentState() != State.IDLE)
//            return;//cannot recieve orders

        Scanner sc = new Scanner(order);
        command = sc.next();
        parameters.clear();
        while (sc.hasNext())
            parameters.add(sc.next());

        //execute
        //TODO: calculate route, walk the way (main line)
        route = mg.calculateRoute(this);
    }

    //protected
    //return map for
    protected Map<Location, Float> calculatePreferredLocation(Collection<Location> variants) {
        HashMap<Location, Float> map = new HashMap<>();
        for(Location l : variants){
            float preferIndex = 0;
            preferIndex += getSin(MortalSins.ANGER) * l.getMonsterFactor();
            preferIndex += (getSin(MortalSins.AVARICE) + getSin(MortalSins.ENVY)) * l.getTreasureFactor() / 2f;
            map.put(l, preferIndex);
        }
        return map;
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
                        heroAutomat.transit(State.FIGHT);
                        startFight((Monster) event.getValue());
                    case TREASURE:
                        heroAutomat.transit(State.SEARCHING);
                        //TODO:grab item
                }
                break;
            case SEARCHING:
                break;
            case FIGHT:
                return;

            case RETURNING:
                break;
        }
    }

    private void startFight(Monster monster) {
        heroAutomat.transit(State.FIGHT_ENGAGE);
        monsterFight = new MonsterFight(this, monster, this::wound, this::collectPrize, this::retreat, this::die);
    }

    private void retreat() {
        //TODO: retreat reaction
        if (monsterFight.getState() == FightState.BAD_RETREAT) {
            //wounded
        } else {
            //successful
        }
    }

    private void wound() {
        //TODO: process wound
        heroAutomat.transit(State.RETURNING);
    }

    private void die() {
        //TODO: hero death
    }

    private void collectPrize() {
        //TODO: boost hero
        for (Buff b : monsterFight.getMonster().getPrizeBuffs()) {
            if (!buffs.contains(b))
                buffs.add(b);
        }
        heroAutomat.transitBack();
    }
    //
}
