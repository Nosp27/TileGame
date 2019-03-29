package map.locations;

import monsters.Monsters;

import java.io.File;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public abstract class LocationFactory {

    public static Random r = new Random();

    public static Location getTower() {
        Location l = new Location("tower");
        l.name = "The Tower";
        l.discover();
        return l;
    }

    public static Location getRandomLocation() {

        switch (r.nextInt(5)) {
            case 0:
                return getPlain();
            default:
                return getWoods();
        }
    }

    public static Location getWoods() {
        Location w = getCustom("woodabitdead", new int[][]{{8, 2}, {2, 0}, {15, 2}});
        w.name = "Wood. Seems to be a bit dead...";
        return w;
    }

    public static Location getPlain() {
        Location f = getCustom("plainsignyellow", new int[][]{{5, 1}, {1, 0}, {17, 3}});
        f.name = "Plains";
        return f;
    }

    private static Location getCustom(String name, int[][] etm_stats) {
        Location l = new Location(name);
        if (etm_stats != null) {
            l.encounterFactor = etm_stats[0][0] + (int) (r.nextGaussian() * etm_stats[0][1]);
            l.treasureFactor = etm_stats[1][0] + (int) (r.nextGaussian() * etm_stats[1][1]);
            l.monsterFactor = etm_stats[2][0] + (int) (r.nextGaussian() * etm_stats[2][1]);
        }

        if(ThreadLocalRandom.current().nextDouble() < .4f)
        l.addMonster(Monsters.getSpider());

        return l;
    }
}