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

    //public accessors
    public Hero getHero() {
        return hero;
    }

    public Monster getMonster(){
        return monster;
    }
    //

    //private accessors
    private int getHeroPower(){
        return hero.getStrength();
    }

    private int getMonsterPower(){
        return monster.getStrength();
    }
    //

    //is hero scared?
    private boolean wantsToRetreat(){
        float chance;
        if(getMonsterPower() > getHeroPower())
            chance = 1f* getMonsterPower() / (1f * getHeroPower() + getMonsterPower());
        else chance = 0f;

        chance += hero.getDreadModifier();

        if(chance >= 1)
            return true;

        if(chance <= 0)
            return false;

        return ThreadLocalRandom.current().nextDouble() < chance;
    }

    private void processRetreat(){
        float successChance = .3f + hero.getRetreatBonus();
        float woundChance = .4f - hero.getRetreatBonus(); // ?

        float random = ThreadLocalRandom.current().nextFloat();
        if(random < successChance)
        {
            //TODO: success
        }
        else if(random < successChance + woundChance)
        {
            //TODO: wounded retreat
        }
        else
        {
            //TODO: fight
        }
    }

    //hero wins if result true
    private boolean calculateWinner(){
        int k = 4;

        int heroPower = getHeroPower();
        int monsterPower = getMonsterPower();

        double x = k * Math.abs(heroPower - monsterPower);
        double p = 1d - 1d / (2*(x+1));

        boolean strongWins = ThreadLocalRandom.current().nextDouble() < p;
        boolean isHeroStrong = Math.max(heroPower, monsterPower) == heroPower;

        return isHeroStrong == strongWins;
    }
}
