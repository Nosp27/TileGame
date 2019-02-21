package heroes;

import mechanics.fight.MonsterFight;

public class BuffFactory {
    public static Buff LustBuff(float power) {
        return new Buff("lust buff") {
            @Override
            public int powerBuff(MonsterFight fight) {
                return (int) (fight.getHero().getSin(Hero.MortalSins.LUST) * power);
            }
        };
    }

    public static Buff raceBuff(float power, String race) {
        return new Buff("race buff") {
            @Override
            public int powerBuff(MonsterFight fight) {
                if (fight.getMonster().getType().equals(race)) {
                    return (int) Math.ceil(power);
                } else return 0;
            }
        };
    }

    public static Buff demonPosession(float power) {
        return new Buff("demon posession") {
            @Override
            public void loseAction(MonsterFight fight) {
                Hero.MortalSins ms = Hero.MortalSins.ANGER;
                Hero h = fight.getHero();
                h.setSin(ms, (int)(h.getSin(ms)*power));
            }
        };
    }

    public static Buff improve(Buff buff1, Buff buff2) {
        if (!buff1.type.equals(buff2.type))
            throw new IllegalArgumentException();

        return new Buff(buff1.type) {
            @Override
            public int powerBuff(MonsterFight fight) {
                return buff1.powerBuff(fight) + buff2.powerBuff(fight);
            }

            @Override
            public int dreadBuff(MonsterFight fight) {
                return buff1.dreadBuff(fight) + buff2.dreadBuff(fight);
            }

            @Override
            public int retreatBuff(MonsterFight fight) {
                return buff1.retreatBuff(fight) + buff2.retreatBuff(fight);
            }

            @Override
            public void winAction(MonsterFight fight) {
                buff1.winAction(fight);
                buff2.winAction(fight);
            }

            @Override
            public void loseAction(MonsterFight fight) {
                buff1.loseAction(fight);
                buff2.loseAction(fight);
            }
        };
    }
}
