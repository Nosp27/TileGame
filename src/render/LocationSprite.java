package render;

import map.MapGenerator;
import map.locations.Location;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class LocationSprite extends Sprite {
    private static final String tileDirectory = "res/tiles/";
    private static final String borderDirectory = "res/tiles/borders";
    private List<Image> borderSprites;

    private Location location;
    LocationAnimator anim;

    public Location getLocation() {
        return location;
    }

    public LocationSprite(Location _location) {
        super(null, -100);
        location = _location;
        readImage(getFirstFrame());
        anim = new LocationAnimator(this);
        initAnimationFrames();
        anim.play();
    }

    @Override
    public void onClick() {
        System.out.println("tile " + location.toString() + " clicked");
    }

    private String getFirstFrame() {
        File[] files = new File(tileDirectory).listFiles();
        for (File f : files) {
            if (f.getPath().contains(location.getPathName())) {
                return f.getPath();
            }
        }
        return null;
    }

    private void initAnimationFrames() {
        Long[] delays = new Long[]{800L, 4300L};

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

    public void generateBorders(List<Location> near) {
        if (borderSprites == null)
            borderSprites = new ArrayList<>();

        if(getColor(location).equals("green"))
            return;

        String[] sides = new String[]{"left", "up", "right", "down"};

        int minSize = Math.min(near.size(), sides.length);

        for(int i = 0; i < minSize; ++i){
            if(getColor(near.get(i)).equals(getColor(location)))
                continue;

            final int ii = i;
            File requiredFile = Arrays.asList(Objects.requireNonNull((new File(borderDirectory)).listFiles()))
                    .stream().filter(x -> x.getName().contains(sides[ii])).findFirst().get();

            try {
                BufferedImage border = ImageIO.read(requiredFile);
                borderSprites.add(border);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static String getColor(Location l){
        String pathName = l.getPathName();

        String[] colors = new String[]{"green", "yellow"};
        for(int i = 0; i < colors.length; ++i){
            if(pathName.contains(colors[i]))
                return colors[i];
        }
        return colors[0];
    }

    @Override
    public void draw(Graphics g, int x, int y, int w, int h) {
        super.draw(g, x, y, w, h);
        if (borderSprites != null) {
            for (Image img : borderSprites)
                g.drawImage(img, x, y, w, h, null);
        }
    }
}
