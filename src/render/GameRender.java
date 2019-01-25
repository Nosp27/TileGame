package render;

import map.MapGenerator;
import map.locations.Location;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameRender extends JPanel{
    static Random r = new Random();

    int sizeX = 120, sizeY = 120;
    int offsetX = 0;
    int offsetY = 0;

    LocationSprite[][] map;

    private MapGenerator generator;

    public GameRender(MapGenerator gen){
        generator = gen;

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                switch (e.getKeyChar())
                {
                    case 'd':offsetX+=50;break;
                    case 'a':offsetX-=50;break;
                    case 'w':offsetY+=50;break;
                    case 's':offsetY-=50;break;
                    default: return;
                }

                repaint();
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        System.out.println("out");

        if(map == null)
        map = getMap(generator);

        for(int i = 0; i < map.length; ++i){
            for(int j = 0; j < map[i].length; ++j){
                map[i][j].draw(g, j * sizeX - offsetX, i * sizeY + offsetY, sizeX, sizeY);
            }
        }
    }

    LocationSprite[][] getMap(MapGenerator generator){
        Location[][] map = generator.getMap();
        final int size = map.length;

        LocationSprite[][] renderMap = new LocationSprite[size][size];
        for(int i = 0; i < renderMap.length; ++i){
            for(int j = 0; j < renderMap[i].length; ++j){
                //spawn next location
                int index = r.nextInt(3) + 1;
                renderMap[i][j] = new LocationSprite(map[i][j], "res/tiles/test_tile" + index + ".jpg");
            }
        }
        return renderMap;
    }
}
