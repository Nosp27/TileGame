package heroes;

import mechanics.fight.MonsterFight;

public abstract class Buff {
    private static int counter = 0;
    String type;

    protected Buff(String _type){
        type = _type;
    }

    public String getType() {
        return type;
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

    public void winAction(MonsterFight fight) {
    }

    public void loseAction(MonsterFight fight) {
    }

    @Override
    public int hashCode() {
        return type.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return hashCode() == obj.hashCode();
    }
}
