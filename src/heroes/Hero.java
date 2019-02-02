package heroes;

import javafx.util.Pair;
import map.MapGenerator;
import map.locations.EventType;
import map.locations.Location;
import mechanics.fight.FightState;
import mechanics.fight.MonsterFight;
import monsters.Monster;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class Hero extends Thread {
    int x, y;
    String pathName;

    private MapGenerator mg;

    int basePower;
    float retreatBonus;
    float dreadModifier;

    //hero control
    private volatile boolean gotNewOrder;
    private volatile String command;
    private volatile LinkedList<String> parameters;
    Stack<Integer[]> route;
    //

    private final HeroAutomat heroAutomat = HeroAutomat.generateAutomat();

    List<Buff> buffs;

    MonsterFight monsterFight;

    //render
    private Runnable walkCallback;
    //

    //constructor
    public Hero(MapGenerator mg) {
        this.mg = mg;
        sins = new HashMap<>(6);
        parameters = new LinkedList<>();
        buffs = new LinkedList<>();
    }

    public void setWalkCallback(Runnable walkCallback) {
        this.walkCallback = walkCallback;
    }

    //region accessors
    public Location getCurrentLocation() {
        return mg.getMap()[y][x];
    }

    public HeroState currentState() {
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

    public String getMission() {
        return command;
    }
    //endregion

    public void run() {
        try {
            synchronized (this){
                while (true) {
                    doSomething();
                    sleep(400);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int getStrength() {
        if (monsterFight == null) {
            return basePower;
        }

        int finalStrength = basePower;
        for (Buff b : buffs) {
            finalStrength += b.powerBuff(monsterFight);
        }
        return finalStrength;
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
    public final Integer[] preferLocation(Stream<Pair<Location, Integer[]>> variants) {
        Float bestChoiceCoeff = 0f;
        Integer[] bestChoice = null;
        Map<Integer[], Float> map = calculatePreferredLocation(variants);
        for (Integer[] cords : map.keySet()) {
            if (bestChoice == null || map.get(cords) > bestChoiceCoeff) {
                bestChoice = cords;
                bestChoiceCoeff = map.get(cords);
            }
        }
        return bestChoice;
    }

    public void giveOrder(String order) {
        synchronized (this){
            Scanner sc = new Scanner(order);
            command = sc.next();
            parameters.clear();
            while (sc.hasNext())
                parameters.add(sc.next());
            gotNewOrder = true;
        }
    }

    private void executeOrder(){
        //execute
        route = mg.calculateRoute(this);

        if (command.equals("seek")) {
            heroAutomat.transit(HeroState.WALKING);
        }
    }

    //protected
    //return map for
    protected Map<Integer[], Float> calculatePreferredLocation(Stream<Pair<Location, Integer[]>> variants) {
        ConcurrentHashMap<Integer[], Float> map = new ConcurrentHashMap<>();
        Iterator<Pair<Location, Integer[]>> it = variants.iterator();
        while (it.hasNext()) {
            Pair<Location, Integer[]> pair = it.next();
            Location l = pair.getKey();
            float preferIndex = 0;
            preferIndex += getSin(MortalSins.ANGER) * l.getMonsterFactor();
            preferIndex += (getSin(MortalSins.AVARICE) + getSin(MortalSins.ENVY)) * l.getTreasureFactor() / 2f;
            map.put(pair.getValue(), preferIndex);
        }
        return map;
    }

    //following methods affect behaviour
    private void doSomething() {
        if(gotNewOrder){
            executeOrder();
            gotNewOrder = false;
        }

        Pair<EventType, Object> event;
        switch (heroAutomat.getCurrentState()) {
            case IDLE:
                //TODO: idle work
                System.out.println("idle");
                break;
            case WALKING:
                System.out.println("walk");
                followRoute();

                event = getCurrentLocation().message_heroCame(this);
                switch (event.getKey()) {
                    case MONSTER:
                        startFight((Monster) event.getValue());
                        break;
                    case TREASURE:
                        heroAutomat.transit(HeroState.SEARCHING);
                        //TODO:grab item
                        break;
                    case NONE:
                        break;
                }
                break;
            case SEARCHING:
                System.out.println("searching");
                heroAutomat.transitBack();
                break;
            case FIGHT:
                System.out.println("fight");
                return;

            case RETURNING:
                System.out.println("returning");
                followRoute();
                break;
        }
    }

    private void followRoute() {
        if (route.empty()) {
            heroAutomat.transit(HeroState.IDLE);
            return;
        }
        System.out.println("walk to " + route.peek()[1] + " " + route.peek()[0]);
        Integer[] preferred = route.pop();
        y = preferred[0];
        x = preferred[1];

        if (walkCallback != null)
            walkCallback.run();
    }

    private void startFight(Monster monster) {
        heroAutomat.transit(HeroState.FIGHT);
        monsterFight = new MonsterFight(this, monster, this::wound, this::collectPrize, this::retreat, this::die);
    }

    private void retreat() {
        //TODO: retreat reaction
        heroAutomat.transitBack();
    }

    private void wound() {
        //TODO: process wound
        heroAutomat.transit(HeroState.RETURNING);
        giveOrder("return");
        route = mg.calculateRoute(this);
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
