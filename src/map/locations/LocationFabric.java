package map.locations;

import javax.swing.filechooser.FileNameExtensionFilter;
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
        return new Location("tower");
    }

    public static Location getRandomLocation(){
        Location l = new Location("random");
        float linear = r.nextFloat();
        float exp = exp(1.0f, linear);
        l.encounterFactor = (int)(exp * 100);
        l.monsterFactor = (int)((l.encounterFactor * 0.5f + (float)r.nextGaussian()*0.5f - 0.25f) * 100);
        l.treasureFactor = (int)((1 - l.encounterFactor) * (float)(r.nextGaussian() * 0.8f + 0.2f) * 100);

        fillImagePaths(l);

        return l;
    }

    private static void fillImagePaths(Location l){
        try{
            for (File f : new File(tileDirectory).listFiles()){
                if(f.getName().contains(l.name)){
                    l.paths.add(f.getPath());
                }
            }
        }
        catch (NullPointerException ignore){}
    }
}
