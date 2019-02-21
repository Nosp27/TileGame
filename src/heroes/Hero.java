package heroes;

import javafx.util.Pair;
import map.MapGenerator;
import map.locations.EventType;
import map.locations.Location;
import mechanics.Logger;
import mechanics.fight.FightState;
import mechanics.fight.MonsterFight;
import monsters.Monster;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

public class Hero extends Thread {
    int x, y;
    String pathName;

    private MapGenerator mg;

    int basePower;
    float retreatBase;
    float dreadBase;

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
        buffs = Collections.synchronizedList(buffs);
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
        if (monsterFight == null)
            return retreatBase;
        float retreatBonus = retreatBase;
        for (Buff buff : buffs) {
            retreatBonus += buff.retreatBuff(monsterFight);
        }
        return retreatBonus;
    }

    public float getDreadModifier() {
        if (monsterFight == null) {
            return dreadBase;
        }
        float dreadModifier = dreadBase;
        for (Buff buff : buffs) {
            dreadModifier += buff.dreadBuff(monsterFight);
        }
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
            synchronized (this) {
                while (true) {
                    doSomething();
                    sleep(400);
                    notifyAll();
                    wait(10);
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
        GLUTTONY;

        public static MortalSins getRandom() {
            switch (ThreadLocalRandom.current().nextInt(5)) {
                case 0:
                    return ANGER;
                case 1:
                    return ENVY;
                case 2:
                    return PRIDE;
                case 3:
                    return AVARICE;
                case 4:
                    return LUST;
                case 5:
                    return GLUTTONY;
            }
            throw new RuntimeException();
        }
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

    public synchronized void giveOrder(String order) {

        if (heroAutomat.getCurrentState() != HeroState.IDLE)
            return;

        giveOrderInternal(order);
    }

    private synchronized void giveOrderInternal(String order) {
        Logger.log(this,0, "order: " + order);
        Scanner sc = new Scanner(order);
        command = sc.next();
        parameters.clear();
        while (sc.hasNext())
            parameters.add(sc.next());
        gotNewOrder = true;
    }

    private void executeOrder() {
        Logger.log(this,1,"execute: " + command);
        //execute
        switch (command) {
            case "seek":
                heroAutomat.transit(HeroState.WALKING);
                route = mg.calculateRoute(this);
                break;
            case "return":
                heroAutomat.transit(HeroState.RETURNING);
                route = mg.calculateRoute(this);
                break;
            case "buffs":
                Logger.log(this,1,buffs.size() + " buffs");
                for (Buff b : buffs) Logger.log(this,2,"\t>buff#" + b.getType());
                break;
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

    //region methods affecting behaviour
    private void doSomething() {
        synchronized (this){
            if (gotNewOrder) {
                executeOrder();
                gotNewOrder = false;
            }
        }

        Pair<EventType, Object> event;
        switch (heroAutomat.getCurrentState()) {
            case IDLE:
                break;
            case WALKING:
                Logger.log(this,1,"walk");
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
                Logger.log(this,1,"searching");
                heroAutomat.transitBack();
                break;
            case FIGHT:
                Logger.log(this,1,"fight");
                return;

            case RETURNING:
                Logger.log(this,1,"returning");
                followRoute();
                break;
        }
    }

    private void followRoute() {
        if (route.empty()) {
            Logger.log(this,1,"idle");
            heroAutomat.transit(HeroState.IDLE);
            return;
        }

        Integer[] preferred = route.pop();
        y = preferred[0];
        x = preferred[1];
        Logger.logInline(this,"go to " + y + " " + x);

        if (walkCallback != null)
            walkCallback.run();
    }

    private void startFight(Monster monster) {
        heroAutomat.transit(HeroState.FIGHT);
        monsterFight = new MonsterFight(this, monster, this::wound, this::collectPrize, this::retreat, this::die);
    }

    private void retreat() {
        heroAutomat.transitBack();
        monsterFight = null;
    }

    private synchronized void wound() {
        //TODO: process wound
        Logger.log(this,2,"wound runs");
        giveOrderInternal("return");
        monsterFight = null;
    }

    private synchronized void die() {
        Logger.log(this,0,"HERO DEAD");
        monsterFight = null;
        this.interrupt();
    }

    private synchronized void collectPrize() {
        addBuffs(monsterFight.getMonster().getPrizeBuffs());

        heroAutomat.transitBack();
        monsterFight = null;
    }
    //endregion

    private synchronized void addBuffs(Collection<Buff> _buffs) {
        Logger.log(this,3,"hero gets buffs");
        for (Buff b : _buffs) {
            Logger.log(this,4,"buff " + b.type);
            if (!buffs.contains(b)){
                Logger.log(this,5,"add");
                buffs.add(b);
            }
            else {
                Logger.log(this,5,"improve");
                Buff b1 = buffs.remove(buffs.indexOf(b));
                buffs.add(BuffFactory.improve(b, b1));
            }
        }
    }
}
