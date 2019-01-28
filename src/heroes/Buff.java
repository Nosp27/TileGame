package heroes;

import heroes.Hero;
import mechanics.fight.MonsterFight;

public abstract class Buff {
    public int powerBuff(MonsterFight fight){return 0;}
    public int dreadBuff(MonsterFight fight){return 0;}
    public int retreatBuff(MonsterFight fight){return 0;}
}
