package mechanics.fight;

import heroes.Hero;
import monsters.Monster;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

public class MonsterFight {
    Hero hero;
    Monster monster;
    int fightDuration = 1200;

    Runnable wound, gain, retreat, die;

    FightState state;

    public MonsterFight(Hero h, Monster m, Runnable wound, Runnable gain, Runnable retreat, Runnable die) {
        this.die = die;
        this.wound = wound;
        this.gain = gain;
        this.retreat = retreat;

        state = FightState.BEGIN;

        System.out.println("met " + m.getType());
        hero = h;
        monster = m;
        if (wantsToRetreat()) {
            System.out.println("want retreat");
            processRetreat();
            System.out.println("tried retreat, state: " + state);
        }

            new Timer("fight timer").schedule(new TimerTask() {
                @Override
                public void run() {
                    finishFight();
                }
            }, fightDuration);
    }

    //public accessors
    public Hero getHero() {
        return hero;
    }

    public Monster getMonster() {
        return monster;
    }
    //

    //private accessors
    private int getHeroPower() {
        return hero.getStrength();
    }

    private int getMonsterPower() {
        return monster.getStrength();
    }
    //

    //is hero scared?
    private boolean wantsToRetreat() {
        float chance;
        if (getMonsterPower() > getHeroPower())
            chance = 1f * getMonsterPower() / (1f * getHeroPower() + getMonsterPower());
        else chance = 0f;

        chance += hero.getDreadModifier();

        if (chance >= 1)
            return true;

        if (chance <= 0)
            return false;

        return ThreadLocalRandom.current().nextDouble() < chance;
    }

    private void processRetreat() {
        float successChance = .3f + hero.getRetreatBonus();
        float woundChance = .4f - hero.getRetreatBonus(); // ?

        float random = ThreadLocalRandom.current().nextFloat();
        if (random < successChance) {
            //TODO: success
            state = FightState.RETREATED;
        } else if (random < successChance + woundChance) {
            //TODO: wounded retreat
            state = FightState.BAD_RETREAT;
        } else {
            //TODO: fight
            state = FightState.PROCESS;
        }
    }

    //hero wins if result true
    private boolean calculateWinner() {
        int k = 4;

        int heroPower = getHeroPower();
        int monsterPower = getMonsterPower();

        double x = k * Math.abs(heroPower - monsterPower);
        double p = 1d - 1d / (2 * (x + 1));

        boolean strongWins = ThreadLocalRandom.current().nextDouble() < p;
        boolean isHeroStrong = Math.max(heroPower, monsterPower) == heroPower;

        return isHeroStrong == strongWins;
    }

    private void finishFight() {
        System.out.println("ff with state " + state.name());
        switch (state){
            case PROCESS: state = FightState.END;
                if (calculateWinner()) {
                    //boost up hero
                    System.out.println("hero win");
                    gain.run();
                } else {
                    System.out.println("hero lose");
                    if (isFatal()) {
                        die.run();
                    } else {
                        //wound hero
                        wound.run();
                    }
                }
                break;
            case BAD_RETREAT:
                wound.run(); break;
            case RETREATED:
                retreat.run(); break;
        }
        state = FightState.END;
    }

    private boolean isFatal() {
        return ThreadLocalRandom.current().nextDouble() < (1f * getHeroPower() / (getHeroPower() + getMonsterPower()));
    }

    public FightState getState() {
        return state;
    }
}
