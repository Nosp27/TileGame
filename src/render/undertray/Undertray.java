package render.undertray;

import heroes.Hero;
import map.locations.Location;
import render.UI.UI_Panel;
import render.undertray.undertrayLayouts.LayoutAbstractFactory;
import render.undertray.undertrayLayouts.UndertrayLayout;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.swing.*;
import java.awt.*;

public class Undertray extends JPanel {
    UI_Panel background;
    public UndertrayLayout layout;

    Undertray(){}

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        background.draw(g, 0,0,getWidth(), getHeight());

        if(layout!=null)
            layout.draw(g);
    }

    public void selectLocation(Location l){
        LayoutAbstractFactory.selectLocation(l, this);
        repaint();
    }

    public void selectHero(Hero h){
        LayoutAbstractFactory.selectHero(h, this);
    }
}
