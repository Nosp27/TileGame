package heroes;

import mechanics.fight.MonsterFight;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * This class provides convinient creation of different {@code Buff} instances
 */
public class BuffFactory {
    public static Buff getLustBuff(float power) {
        return new Buff() {
            @Override
            public int powerBuff(MonsterFight fight) {
                return (int) (fight.getHero().getSin(Hero.MortalSins.LUST) * power);
            }
        };
    }

    public static Buff raceBuff() {//TODO: implement race buff
        throw new NotImplementedException();
    }
}
