package render;

import map.MapGenerator;
import map.locations.Location;
import mechanics.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;
import java.awt.image.BufferedImage;
import java.util.Stack;

public class GameFrame extends JFrame {
    public GameRender mainPanel;

    public GameFrame(MapGenerator mg) {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        setSize(screenSize.width / 2, screenSize.height);
        setLocation(screenSize.width / 2, 0);

        mainPanel = new GameRender(mg);
        mainPanel.setBackground(Color.BLACK);
        mainPanel.setVisible(true);
        add(mainPanel);
        mainPanel.setFocusable(true);
        setFocusable(false);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Logger.finish();
            }
        });

        setVisible(true);
    }
}