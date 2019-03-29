package render.undertray.undertrayLayouts;

import heroes.Hero;
import map.locations.Location;
import render.UI.UI_Button;
import render.UI.UI_Panel;

import javax.swing.*;
import java.awt.*;

class LocationLayout implements UndertrayLayout {
    private String locationDescription;
    private UI_Panel descriptionBackground;
    private UI_Button[] tabs;

    private Location selected;

    private boolean initDone = false;

    private void init() {
        if (initDone)
            return;

        descriptionBackground = new UI_Panel("res/UI/ui_panel.png");

        tabs = new UI_Button[3];

//        for(int i = 0; i < 3; ++i) {
//            tabs[i] = new Buttons()
//                    //TODO: paths
//                    .setState(Buttons.ButtonState.CHECK_UNCHECK)
//                    .build();
//
//            TODO: set correct switch
//            tabs[i].listenerList.add(null);
//        }

        initDone = true;
    }

    private void clearLayout() {
        //TODO: clear
    }

    //////////////////////////////////////////////
    public void selectLocation(Location l) {
        init();
        selected = l;
        clearLayout();
        locationDescription = l.getName();
    }

    public void selectHero(Hero h) {
        throw new RuntimeException();
    }

    public void draw(Graphics g) {
        int w = g.getClipBounds().width / 2;
        int h = g.getClipBounds().height / 4;

        int x = w / 3;
        int y = h / 2;

        descriptionBackground.plant(x,y);
        descriptionBackground.draw(g, 0, 0, w, h);

        g.drawString(locationDescription, x + 20,y + 20);

    }
}
