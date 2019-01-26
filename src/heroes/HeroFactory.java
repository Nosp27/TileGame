package heroes;

import java.util.NoSuchElementException;

public class HeroFactory {
    static final String directory = "res/heroes/";

    public static Hero sorcerer() {
        //TODO: sorcerer
        Hero h = getCustomHero();
        h.pathName = "sorcer";
        h.pathName = getPath(h);
        return h;
    }

    public static void place(Hero h, int[] cords) {
        h.x = cords[0];
        h.y = cords[1];
    }

    private static Hero getCustomHero() {
        Hero h = new Hero();
        h.pathName = "hero";
        h.pathName = getPath(h);
        return h;
    }

    private static String getPath(Hero h) {
        return directory + h.pathName + ".png";
    }
}
