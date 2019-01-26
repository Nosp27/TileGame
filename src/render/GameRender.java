package render;

import heroes.Hero;
import map.MapGenerator;
import map.locations.Location;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import java.util.stream.Stream;

public class GameRender extends JPanel {
    static Random r = new Random();

    int sizeX = 120, sizeY = 120;
    int offsetX = 0;
    int offsetY = 0;

    LocationSprite[][] map;

    List<HeroSprite> heroes;

    private MapGenerator generator;

    public GameRender(MapGenerator gen) {
        generator = gen;
        heroes = new LinkedList<>();
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                switch (e.getKeyChar()) {
                    case 'd':
                        offsetX += 50;
                        break;
                    case 'a':
                        offsetX -= 50;
                        break;
                    case 'w':
                        offsetY += 50;
                        break;
                    case 's':
                        offsetY -= 50;
                        break;
                    default:
                        return;
                }
                repaint();
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                switch (e.getButton()) {
                    case MouseEvent.BUTTON1:
                        List<Sprite> clicked = new LinkedList<>();
                        for (Sprite s : getAllSprites())
                            if (s.checkClick(e.getPoint()))
                                clicked.add(s);

                        if (clicked.size() == 0)
                            return;

                        clicked.sort(Comparator.comparingInt(o -> o.order));
                        ((LinkedList<Sprite>) clicked).getFirst().onClick();
                        break;

                    case MouseEvent.BUTTON3:
                        List<Sprite> clickedMap = new LinkedList<>();
                        for (Sprite[] ss : map)
                            for (Sprite s : ss)
                                if (s.checkClick(e.getPoint()))
                                    clickedMap.add(s);

                        if (clickedMap.size() == 0)
                            return;

                        clickedMap.sort(Comparator.comparingInt(o -> o.order));
                        ((LinkedList<Sprite>) clickedMap).getFirst().onClick();
                        break;
                }
            }
        });
    }

    private Sprite[] getAllSprites() {
        Stream s = heroes.stream();
        return (Sprite[]) s.toArray();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        drawMap(g);

        drawHeroes(g);
    }

    private void drawMap(Graphics g) {
        if (map == null)
            map = getMap();

        for (int i = 0; i < map.length; ++i) {
            for (int j = 0; j < map[i].length; ++j) {
                map[i][j].draw(g, j * sizeX - offsetX, i * sizeY + offsetY, sizeX, sizeY);
            }
        }
    }

    private void drawHeroes(Graphics g) {
        if (heroes.size() == 0)
            heroes = getHeroes();

        for (HeroSprite hs : heroes) {
            hs.draw(g, hs.hero.getX() * sizeX + sizeX / 2 - offsetX, hs.hero.getY() * sizeY + sizeY / 2 - offsetY, 0,0);
        }
    }

    List<HeroSprite> getHeroes() {
        List<HeroSprite> resultList = new LinkedList<>();
        for (Hero h : generator.getHeroes()) {
            resultList.add(new HeroSprite(h));
        }
        return resultList;
    }

    LocationSprite[][] getMap() {
        Location[][] loc_map = generator.getMap();
        final int size = loc_map.length;

        LocationSprite[][] renderMap = new LocationSprite[size][size];
        for (int i = 0; i < renderMap.length; ++i) {
            for (int j = 0; j < renderMap[i].length; ++j) {
                //spawn next location
                renderMap[i][j] = new LocationSprite(loc_map[i][j], loc_map[i][j].getRandomTile());
            }
        }
        return renderMap;
    }
}
