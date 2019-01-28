package heroes;

import mechanics.fight.MonsterFight;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class BuffFactory {
    public Buff getLustBuff(float power) {
        return new Buff() {
            @Override
            public int powerBuff(MonsterFight fight) {
                return (int) (fight.getHero().getSin(Hero.MortalSins.LUST) * power);
            }
        };
    }

    public Buff raceBuff() {//TODO: implement race buff
        throw new NotImplementedException();
    }
}
