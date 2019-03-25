package render;
import map.MapGenerator;
import render.tower.TowerBuilder;
import render.tower.TowerPanel;
import render.undertray.Undertray;
import render.undertray.UndertrayBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class GameFrame extends JFrame {
    public GameRender mainPanel;
    public Undertray undertray;

    public GameFrame(MapGenerator mg){
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        setSize(screenSize.width / 2, screenSize.height);
        setLocation(screenSize.width/2, 0);

        getContentPane().setLayout(new BorderLayout(10,10));
        getContentPane().setBackground(Color.BLACK);

        mainPanel = new GameRender(mg, this);
        mainPanel.setBackground(Color.BLACK);
        linkResize(mainPanel, .6f, 0);

        mainPanel.setVisible(true);
        add(mainPanel, BorderLayout.CENTER);

        mainPanel.setFocusable(true);
        setFocusable(false);

        TowerPanel tower = (new TowerBuilder()).background("res/UI/hero_labels/Tower.png").build();
        linkResize(tower, .4f, 0);
        add(tower, BorderLayout.EAST);

        undertray = new UndertrayBuilder().background("res/UI/hero_labels/Undertray.png").build();
        linkResize(undertray, 0, .2f);
        add(undertray, BorderLayout.SOUTH);

        setVisible(true);
    }

    void linkResize(Component c, float w, float h){
        Dimension size = getSize();
        c.setPreferredSize(new Dimension((int)(size.width * w),(int)(size.height * h)));
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Dimension size = getSize();
                c.setPreferredSize(new Dimension((int)(size.width * w),(int)(size.height * h)));
            }
        });
    }
}