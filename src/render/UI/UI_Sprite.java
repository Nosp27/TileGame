package render.UI;

import render.Sprite;

import java.awt.*;

public abstract class UI_Sprite extends Sprite {
    protected boolean isVisible;

    protected void setVisible(boolean v) {
        isVisible = v;
    }

    protected int ui_x, ui_y;

    UI_Sprite(String path) {
        super(path, 120);
    }

    public void plant(int x, int y) {
        ui_x = x;
        ui_y = y;
        setVisible(true);
    }

    public void hide() {
        setVisible(false);
    }

    @Override
    public void draw(Graphics g, int x, int y, int w, int h) {
        if (!isVisible)
            return;

        super.draw(g, x, y, w, h);
    }
}
