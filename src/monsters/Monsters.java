package monsters;

import heroes.BuffFactory;

public class Monsters {
    public static Monster getSpider(){
        Monster spider = getCustom();
        spider.type = "spider";
        spider.path = "res/monsters/spider.png";
        spider.strength = 3;
        return spider;
    }

    private static Monster getCustom(){
        Monster m = new Monster();
        return m;
    }
}
