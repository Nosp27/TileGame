package heroes;

import heroes.heroStateMachine.*;
import javafx.util.Pair;
import map.MapGenerator;
import map.locations.Location;
import mechanics.fight.MonsterFight;
import monsters.Monster;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

/**
 * Represents a hero that controls himself in own thread.
 */
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

    List<Buff> buffs;

    MonsterFight monsterFight;

    //render walking
    private Runnable walkCallback;
    //

    //constructor

    //state pattern
    private HeroState idleState;
    private HeroState walkingState;
    private HeroState searchingState;
    private HeroState fightState;
    private HeroState returningState;

    private HeroState currentState;
    private Stack<HeroState> previousStates;
    ///

    public HeroState getWalkingState() {
        return walkingState;
    }

    public HeroState getSearchingState() {
        return null;
    }
    ///

    /**
     * Creating new hero. Hero needs to be aware, on what map is he located
     * So necessary to attach {@code MapGenerator}
     *
     * @param mg {@code MapGenerator}, linked to hero
     */
    public Hero(MapGenerator mg) {
        this.mg = mg;
        sins = new HashMap<>(6);
        parameters = new LinkedList<>();
        buffs = new LinkedList<>();

        //state
        previousStates = new Stack<>();

        HeroProxy hp = generateHeroProxy();
        idleState = new IdleState(hp);
        walkingState = new WalkingState(hp);
        searchingState = new SearchingState(hp);
        fightState = new FightState(hp);
        returningState = new ReturningState(hp);

        currentState = idleState;
        //////
    }

    /**
     * Walk Callback is needed for internal messaging (e.g for renderer)
     * When hero walks he sends a message, so other component can redraw him
     *
     * @param walkCallback callback that is to be assigned
     */
    public void setWalkCallback(Runnable walkCallback) {
        this.walkCallback = walkCallback;
    }

    //region accessors
    public Location getCurrentLocation() {
        return mg.getMap()[y][x];
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * Path Name Getter
     *
     * @return path to hero's sprite file
     */
    public String getPathName() {
        return pathName;
    }


    /**
     * Bonus, used when calculating chances for hero to retreat
     *
     * @return bonus, that can be added to critical value
     * or null if sin didn't exist for the hero before
     */
    public float getRetreatBonus() {
        if (monsterFight == null)
            return retreatBase;
        float retreatBonus = retreatBase;
        for (Buff buff : buffs) {
            retreatBonus += buff.retreatBuff(monsterFight);
        }
        return retreatBonus;
    }

    /**
     * Bonus, used when calculating chances for hero to be scared and try to retreat
     *
     * @return bonus, that can be added to critical value
     */
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

    /**
     * Get value of given Sin
     *
     * @param sinType enum, representing what sin is requested
     * @return Sin value
     */
    public Integer getSin(MortalSins sinType) {
        return sins.getOrDefault(sinType, 0);
    }

    /**
     * Method sets new sin value to hero
     *
     * @param sinType what sin is wanted to modify
     * @param value   what value you want to assign [0;100]
     * @return previous sin value
     */
    public Integer setSin(MortalSins sinType, Integer value) {
        if (value < 0 || value > 100)
            throw new IllegalArgumentException();
        return sins.put(sinType, value);
    }

    /**
     * Request activity that hero executes
     *
     * @return Command given
     */
    public String getMission() {
        return command;
    }

    public HeroState getHeroState() {
        return currentState;
    }
    //endregion

    /**
     * Method with hero activity
     * No need to call directly, hero activity begins when calling {@code start()}
     */
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

    /**
     * Method calculates the current strength of a hero
     * Takes base power and adds all buff modifications
     *
     * @return Final Power
     */
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

    public void giveOrder(String s) {
        currentState.giveOrder(s);
    }

    //region mortal sins

    /**
     * <p>Represents all kinds of {@code MortalSins}, supported by the game</p>
     * <p>ANGER:
     * <br>+ makes hero more powerful
     * <br>- makes hero dangerous to everyone
     * <br>- less retreat chance
     * <br>- can make a mess in the Tower
     * </p>
     * <p>
     * treat: Trainings, Poisons
     * </p>
     * <p>ENVY:
     * <br>+ better use of infernal items
     * <br>+ tends to gain XP
     * <br>- can deny working with high-leveled heroes
     * <br>- can betray his mate
     * </p>
     * <p>PRIDE:
     * <br>+ better teaching
     * <br>+ better learning spells
     * <br>+ accepts more complicated quests
     * <br>- denies easy quests
     * <br>- tends to walk alone
     * </p>
     * <p>AVARICE:
     * <br>+ better gaining items and money
     * <br>- doesn't share finding
     * <br>- easy to become possessed by demons
     * </p>
     * <p>LUST:
     * <br>+ easier to make arrangements
     * <br>+ can recruit rivals
     * <br>+ can solve the conflicts with civilians
     * <br>- can be seduces by a rival
     * <br>- can harass mates
     * </p>
     * <p>GLUTTONY:
     * <br>+ resistible to physical damage
     * <br>- useless versus quick enemies
     * <br>- always must be full before embarking
     * </p>
     */
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

    //public methods

    /**
     * hero can decide, where to go. in this method he gets a Stream pair
     * and prefers where he wants to go
     *
     * @param variants Location mapped to its coordinates
     * @return coordinates of desired location
     */
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


    /**
     * here you can give order for a hero, that will be executed.
     * orders can be recieved only in IDLE state
     * you can't order, while hero is busy
     *
     * @param order command to execute
     */

    private void giveOrderInternal(String order) {
        System.out.println("order: " + order);
        Scanner sc = new Scanner(order);
        command = sc.next();
        parameters.clear();
        while (sc.hasNext())
            parameters.add(sc.next());
        gotNewOrder = true;
    }

    private void executeOrder() {
        System.out.println("execute: " + command);
        //execute
        switch (command) {
            case "seek":
                route = mg.calculateRoute(this);
                setState(walkingState);
                break;
            case "return":
                setState(returningState);
                route = mg.calculateRoute(this);
                break;
            case "buffs":
                System.out.println(buffs.size() + " buffs");
                for (Buff b : buffs) System.out.println("\t>buff#" + b.getID());
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

    //following methods affect behaviour
    private void doSomething() {
        if (gotNewOrder) {
            executeOrder();
            gotNewOrder = false;
        }

        currentState.executeLoop();
    }

    private void followRoute() {
        if (route.empty()) {
            System.out.println("idle");
            setState(idleState);
            return;
        }

        Integer[] preferred = route.pop();
        y = preferred[0];
        x = preferred[1];
        System.out.println("go to " + y + " " + x);

        if (walkCallback != null)
            walkCallback.run();

        getCurrentLocation().discover();
        for (Location l : mg.getNearbyLocations(x, y))
            l.discover();
    }

    private void startFight(Monster monster) {
        setState(fightState);
        monsterFight = new MonsterFight(this, monster, this::wound, this::collectPrize, this::retreat, this::die);
    }

    private void retreat() {
        popState();
        monsterFight = null;
    }

    private void wound() {
        //TODO: process wound
        giveOrderInternal("return");
        monsterFight = null;
    }

    private void die() {
        //TODO: hero death
        monsterFight = null;
    }

    private void collectPrize() {
        for (Buff b : monsterFight.getMonster().getPrizeBuffs()) {
            if (!buffs.contains(b))
                buffs.add(b);
        }
        popState();
        monsterFight = null;
    }

    private void setState(HeroState newState) {
        previousStates.push(currentState);
        currentState = newState;
    }

    private void popState() {
        HeroState prev = previousStates.pop();
        if (prev != null)
            currentState = prev;
    }
    //

    private HeroProxy generateHeroProxy() {
        return new HeroProxy() {
            @Override
            public void followRoute() {
                Hero.this.followRoute();
            }

            @Override
            public Hero getHero() {
                return Hero.this;
            }

            @Override
            public Location getCurrentLocation() {
                return Hero.this.getCurrentLocation();
            }

            @Override
            public void startFight(Monster m) {
                Hero.this.startFight(m);
            }

            @Override
            public void setState(HeroState state) {
                Hero.this.setState(state);
            }

            @Override
            public void setPreviousState() {
                popState();
            }

            @Override
            public void giveOrder(String s) {
                Hero.this.giveOrderInternal(s);
            }
        };
    }

    public interface HeroProxy {
        void followRoute();

        Hero getHero();

        Location getCurrentLocation();

        void startFight(Monster m);

        void setState(HeroState state);

        void setPreviousState();

        void giveOrder(String s);
    }
}
