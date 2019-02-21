package monsters;

import heroes.BuffFactory;

public class Monsters {
    public static Monster getSpider(){
        Monster spider = getCustom();
        spider.type = "spider";
        spider.strength = 3;
        return spider;
    }

    public static Monster getWolf(){
        Monster wolf = getCustom();
        wolf.type = "wolf";
        wolf.strength = 4;
        wolf.prizeBuffs.add(BuffFactory.raceBuff(2.3f, "wolf"));
        return wolf;
    }

    public static Monster getOccult(){
        Monster oc = getCustom();
        oc.strength = 7;
        oc.type = "occult";
        oc.prizeBuffs.add(BuffFactory.demonPosession(4));
        return oc;
    }

    private static Monster getCustom(){
        Monster m = new Monster();
        return m;
    }
}
