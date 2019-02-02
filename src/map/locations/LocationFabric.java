package map.locations;

import monsters.Monsters;

import java.io.File;
import java.util.Random;

public abstract class LocationFabric {
    private static final String tileDirectory = "res/tiles/";

    public static Random r = new Random();

    public static int counter = 0;

    private static float exp(float lyambda, float linear) {
        return (float) (-linear * Math.log1p(linear / lyambda - 1) / lyambda - linear / lyambda);
    }

    public static Location getTower() {
        Location l = new Location("tower");
        fillImagePaths(l);
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
        Location w = getCustom("wood", new int[][]{{8, 2}, {2, 0}, {15, 2}});
        fillImagePaths(w);
        return w;
    }

    public static Location getPlain() {
        Location f = getCustom("plain", new int[][]{{5, 1}, {1, 0}, {17, 3}});
        fillImagePaths(f);
        return f;
    }

    private static Location getCustom(String name, int[][] etm_stats) {
        Location l = new Location(name);
        if (etm_stats != null) {
            l.encounterFactor = etm_stats[0][0] + (int) (r.nextGaussian() * etm_stats[0][1]);
            l.treasureFactor = etm_stats[1][0] + (int) (r.nextGaussian() * etm_stats[1][1]);
            l.monsterFactor = etm_stats[2][0] + (int) (r.nextGaussian() * etm_stats[2][1]);
        }

        l.monsters.add(Monsters.getSpider());
        return l;
    }

    private static void fillImagePaths(Location l) {
        try {
            for (File f : new File(tileDirectory).listFiles()) {
                if (f.getName().contains(l.pathName)) {
                    l.paths.add(f.getPath());
                }
            }
        } catch (NullPointerException ignore) {
        }
    }
}
