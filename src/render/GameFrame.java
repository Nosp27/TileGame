package render;
import map.MapGenerator;
import map.locations.Location;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Stack;

public class GameFrame extends JFrame {
    public GameRender mainPanel;

    public GameFrame(MapGenerator mg){
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        setSize(screenSize.width / 2, screenSize.height);
        setLocation(screenSize.width/2, 0);

        mainPanel = new GameRender(mg);
        mainPanel.setBackground(Color.BLACK);
        mainPanel.setVisible(true);
        add(mainPanel);
        mainPanel.setFocusable(true);
        setFocusable(false);

        setVisible(true);
    }
}