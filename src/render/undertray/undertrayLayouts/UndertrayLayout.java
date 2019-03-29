package render.undertray.undertrayLayouts;

import heroes.Hero;
import map.locations.Location;
import render.UI.UI_Panel;
import render.UI.UI_Sprite;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public interface UndertrayLayout {
    public abstract void selectLocation(Location l);
    public abstract void selectHero(Hero h);

    void draw(Graphics g);
}
