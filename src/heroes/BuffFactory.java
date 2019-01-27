package heroes;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class BuffFactory {
    public Buff getLustBuff(float power) {
        return new Buff() {
            @Override
            public int calculateBuff(Hero h) {
                return (int) (h.getSin(Hero.MortalSins.LUST) * power);
            }
        };
    }

    public Buff raceBuff() {//TODO: implement race buff
        throw new NotImplementedException();
    }
