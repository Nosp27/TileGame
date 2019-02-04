package render;

import map.locations.Location;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

public class LocationSprite extends Sprite{
    private static final String tileDirectory = "res/tiles/";
    private Location location;
    LocationAnimator anim;
    public Location getLocation() {
        return location;
    }

    public LocationSprite(Location _location){
        super(null, -100);
        location = _location;
        readImage(getFirstFrame());
        anim = new LocationAnimator(this);
        initAnimationFrames();
        anim.play();
    }

    @Override
    public void onClick(){
        System.out.println("tile "+ location.toString() + " clicked");
    }

    private String getFirstFrame(){
        File[] files = new File(tileDirectory).listFiles();
        for (File f : files) {
            if (f.getPath().contains(location.getPathName())) {
                return f.getPath();
            }
        }
        return null;
    }

    private void initAnimationFrames() {
        Long[] delays = new Long[]{300L, 300L};

        String name = location.getPathName();

        File[] files = new File(tileDirectory).listFiles();
        Arrays.sort(files, Comparator.comparing(File::getName));

        int i = 0;
        for (File f : files) {
            if (f.getPath().contains(name)) {
                anim.addFrame(f.getPath(), delays[i++ % delays.length]);
            }
        }
    }
}
