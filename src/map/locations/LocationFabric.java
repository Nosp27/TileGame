package map.locations;

import java.io.File;
import java.util.Random;

public abstract class LocationFabric {
    private static final String tileDirectory = "res/tiles/";

    public static Random r = new Random();

    public static int counter = 0;

    private static float exp(float lyambda, float linear){
        return (float)(-linear * Math.log1p(linear/lyambda - 1) / lyambda - linear / lyambda);
    }

    public static Location getTower(){
        Location l =  new Location("tower");
        fillImagePaths(l);
        return l;
    }

    public static Location getRandomLocation(){

        if(r.nextFloat() > 0.33){
            switch (r.nextInt(2))
            {
                case 0: return getField();
                case 1: return getWoods();
            }
        }

        Location l = new Location("random");
        float linear = r.nextFloat();
        float exp = exp(1.0f, linear);
        l.encounterFactor = (int)(exp * 100);
        l.treasureFactor = (int)((1 - l.encounterFactor) * (float)(r.nextGaussian() * 0.8f + 0.2f) * 100);
        l.monsterFactor = (int)((l.encounterFactor * 0.5f + (float)r.nextGaussian()*0.5f - 0.25f) * 100);

        fillImagePaths(l);

        return l;
    }

    public static Location getWoods(){
        Location w = getCustom("woods", new int[][]{{8,2},{2,0},{15,2}});
        fillImagePaths(w);
        return w;
    }

    public static Location getField(){
        Location f = getCustom("fields", new int[][]{{5,1},{1,0},{17,3}});
        fillImagePaths(f);
        return f;
    }

    private static Location getCustom(String name, int[][] etm_stats){
        Location l = new Location(name);
        l.encounterFactor = etm_stats[0][0] + (int)(r.nextGaussian() * etm_stats[0][1]);
        l.treasureFactor = etm_stats[1][0] + (int)(r.nextGaussian() * etm_stats[1][1]);
        l.monsterFactor = etm_stats[2][0] + (int)(r.nextGaussian() * etm_stats[2][1]);

        return l;
    }

    private static void fillImagePaths(Location l){
        try{
            for (File f : new File(tileDirectory).listFiles()){
                if(f.getName().contains(l.pathName)){
                    l.paths.add(f.getPath());
                }
            }
        }
        catch (NullPointerException ignore){}
    }
}
