package render;

import heroes.Hero;
import map.MapGenerator;
import map.locations.Location;
import render.UI.UI_Button;
import render.UI.UI_Panel;
import render.UI.UI_Sprite;

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
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

public class GameRender extends JPanel {
    static Random r = new Random();

    int sizeX = 100, sizeY = 100;
    int offsetX = 0;
    int offsetY = 0;

    LocationSprite[][] map;

    List<HeroSprite> heroes;
    List<UI_Sprite> ui_sprites;

    UI_Panel ctxMenuPanel;
    UI_Button ctx_btn;

    private MapGenerator generator;

    public GameRender(MapGenerator gen) {
        generator = gen;
        heroes = new LinkedList<>();
        ui_sprites = new LinkedList<>();

        ctxMenuPanel = new UI_Panel("res/UI/ui_panel.png");
        ctx_btn = new UI_Button("res/UI/ui_button.png", this::repaint);
        ctxMenuPanel.addChild(ctx_btn);

        ui_sprites.add(ctxMenuPanel);
        ui_sprites.add(ctx_btn);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                switch (e.getKeyChar()) {
                    case 'd':
                        offsetX += sizeX;
                        break;
                    case 'a':
                        offsetX -= sizeX;
                        break;
                    case 'w':
                        offsetY += sizeY;
                        break;
                    case 's':
                        offsetY -= sizeY;
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
                //hideTileMenu();

                switch (e.getButton()) {
                    case MouseEvent.BUTTON1:
                        LinkedList<Sprite> clicked = new LinkedList<>();
                        getAllSprites().forEach(s ->
                        {
                            if (s.checkClick(e.getPoint()))
                                clicked.add(s);
                        });

                        if (clicked.size() == 0)
                            return;

                        clicked.sort(Comparator.comparingInt(o -> o.order));
                        clicked.getLast().onClick();
                        break;

                    case MouseEvent.BUTTON3:
                        LinkedList<Sprite> clickedMap = new LinkedList<>();
                        for (Sprite[] ss : map)
                            for (Sprite s : ss)
                                if (s.checkClick(e.getPoint()))
                                    clickedMap.add(s);

                        if (clickedMap.size() == 0)
                            return;

                        clickedMap.getFirst().onClick();
                        getTileMenu(clickedMap.element());
                        break;
                    default:
                        return;
                }
            }
        });
    }

    private void getTileMenu(Sprite sp) {
        int x = (int) sp.getRect().getCenterX();
        int y = (int) sp.getRect().getCenterY();

        //hardcoded^ offset is already contained, needs to be removed
        ctxMenuPanel.plant(x + offsetX, y - offsetY);

        repaint();
    }

    private void hideTileMenu() {
        ctxMenuPanel.hide();
        repaint(ctxMenuPanel.getRect());
    }

    private Stream<? extends Sprite> getAllSprites() {
        Stream<? extends Sprite> s = heroes.stream();
        s = Stream.concat(s, ui_sprites.stream());
        return s;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        drawMap(g);

        drawHeroes(g);

        drawUI(g);
    }

    private void drawMap(Graphics g) {
        ExecutorService es = Executors.newCachedThreadPool();
        ExecutorCompletionService<Boolean> ecs = new ExecutorCompletionService<>(es);

        if (map == null)
            map = getMap();

        for (int i = 0; i < map.length; ++i) {
            for (int j = 0; j < map[i].length; ++j) {
                final int ii = i;
                final int jj = j;
                final LocationSprite ls = map[i][j];
                ecs.submit(() -> {ls.draw(g, jj * sizeX - offsetX, ii * sizeY + offsetY, sizeX, sizeY); return true;});
            }
        }
        for(int i = 0; i < map.length * map.length; i++){
            try {
                ecs.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void drawHeroes(Graphics g) {
        if (heroes.size() == 0)
            heroes = getHeroes();

        for (HeroSprite hs : heroes) {
            hs.draw(g, hs.hero.getX() * sizeX + sizeX / 2 - offsetX, hs.hero.getY() * sizeY + sizeY / 2 + offsetY, 0, 0);
        }
    }

    private void drawUI(Graphics g) {
        for (UI_Sprite s : ui_sprites)
            s.draw(g, offsetX, offsetY, 0, 0);
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
