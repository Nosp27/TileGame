package heroes;

import mechanics.fight.MonsterFight;

/**
 * Buff is something that gives additional
 * adjustments to hero parameters
 * e.g Buff can improve {@code power} or increase/decrease {@code Sin} value
 */
public abstract class Buff {
    private static int counter = 0;
    private int id;

    {
        id = counter++;
    }

    public int getID() {
        return id;
    }

    public int powerBuff(MonsterFight fight) {
        return 0;
    }

    public int dreadBuff(MonsterFight fight) {
        return 0;
    }

    public int retreatBuff(MonsterFight fight) {
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (!obj.getClass().equals(getClass()))
            return false;
        return getID() == ((Buff) obj).getID();
    }

    @Override
    public int hashCode() {
        return ((Integer)id).hashCode();
    }
}
