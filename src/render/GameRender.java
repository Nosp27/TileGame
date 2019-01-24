package render;

import javafx.scene.input.KeyCode;

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

    Image[][] map;

    public GameRender(){
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
        map = getMap(17);

        for(int i = 0; i < map.length; ++i){
            for(int j = 0; j < map[i].length; ++j){
                g.drawImage(map[i][j], j * sizeX - offsetX, i * sizeY + offsetY, sizeX, sizeY, null);
            }
        }
    }

    Image[][] getMap(int size){
        Image[][] map = new Image[size][size];
        for(int i = 0; i < map.length; ++i){
            for(int j = 0; j < map[i].length; ++j){
                int index = r.nextInt(3) + 1;
                map[i][j] = new ImageIcon("res/tiles/test_tile" + index + ".jpg").getImage();
            }
        }
        return map;
    }

    public static void main(String[] args) {
        new Frame();
    }
}

class Frame extends JFrame {
    GameRender mainPanel;

    public Frame(){
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);

        mainPanel = new GameRender();
        mainPanel.setBackground(Color.BLACK);
        mainPanel.setVisible(true);
        add(mainPanel);
        mainPanel.setFocusable(true);
        setFocusable(false);

        setVisible(true);
    }
}
