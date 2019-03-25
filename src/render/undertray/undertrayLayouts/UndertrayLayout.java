package render.undertray.undertrayLayouts;

import heroes.Hero;
import map.locations.Location;
import render.UI.UI_Panel;
import render.UI.UI_Sprite;

import java.awt.*;
import java.util.ArrayList;

public interface UndertrayLayout {
    void selectLocation(Location l);
    void selectHero(Hero h);

    void draw(Graphics g);
}
