package heroes;

import map.MapGenerator;

import java.util.NoSuchElementException;

public class HeroFactory {
    static final String directory = "res/heroes/";

    public static Hero sorcerer(MapGenerator mg) {
        Hero h = getCustomHero(mg);
        h.pathName = "sorcer";
        h.pathName = getPath(h);
        h.basePower = 8;
        return h;
    }

    public static void place(Hero h, int[] cords) {
        h.x = cords[0];
        h.y = cords[1];
    }

    private static Hero getCustomHero(MapGenerator mg) {
        Hero h = new Hero(mg);
        h.pathName = "hero";
        h.pathName = getPath(h);
        return h;
    }

    private static String getPath(Hero h) {
        return directory + h.pathName + ".png";
    }
}
