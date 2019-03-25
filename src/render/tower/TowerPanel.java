package render.tower;

import render.Sprite;
import render.UI.UI_Button;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TowerPanel extends JPanel {
    Sprite background;
    ArrayList<UI_Button> upgradeButtons;

    TowerPanel(){
        super();
        upgradeButtons = new ArrayList<>();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        background.draw(g, 0,0, getWidth(),getHeight());
    }
}
