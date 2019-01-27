package mechanics.fight;

import heroes.Hero;
import monsters.Monster;

import java.util.concurrent.ThreadLocalRandom;

public class MonsterFight {
    Hero hero;
    Monster monster;

    public MonsterFight(Hero h, Monster m){
        hero = h;
        monster = m;
    }

    //hero wins if result true
    private boolean calculateWinner(){
        int k = 4;

        int heroPower = hero.getStrength();
        int monsterPower = monster.getStrength();

        double x = k * Math.abs(heroPower - monsterPower);
        double p = 1d - 1d / (2*(x+1));

        boolean strongWins = ThreadLocalRandom.current().nextDouble() < p;
        boolean isHeroStrong = Math.max(heroPower, monsterPower) == heroPower;

        return isHeroStrong == strongWins;
    }
}
