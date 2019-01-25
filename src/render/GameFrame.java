package render;
import map.MapGenerator;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    GameRender mainPanel;

    public GameFrame(MapGenerator mg){
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);

        mainPanel = new GameRender(mg);
        mainPanel.setBackground(Color.BLACK);
        mainPanel.setVisible(true);
        add(mainPanel);
        mainPanel.setFocusable(true);
        setFocusable(false);

        setVisible(true);
    }
}