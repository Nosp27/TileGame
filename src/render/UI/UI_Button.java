package render.UI;

import render.SpriteAnimator;
import sun.font.FontFamily;

import java.awt.*;

public class UI_Button extends UI_Sprite implements Child {
    private String text = "sas";
    Point textOffset = new Point(3, 11);
    ButtonAnimator animator;

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public UI_Button(String path, Runnable update) {
        super(path);
        animator = new ButtonAnimator(path, update, this);

        //TODO: correct line
        animator.addState("click", "res/heroes/sorcer.png", 70);
        order = 125;
    }

    private Parent parent;

    @Override
    public Parent getParent() {
        return parent;
    }

    @Override
    public void setParent(Parent parent) {
        this.parent = parent;
    }

    @Override
    public void draw(Graphics g, int x, int y, int w, int h) {
        if (parent != null) {
            x = -x + ui_x;
            y += ui_y;
        }
        super.draw(g, x, y, w, h);

        if (isVisible) {
            g.setFont(g.getFont().deriveFont(7f));
            g.setColor(Color.WHITE);
            g.drawString(text, x + textOffset.x, y + textOffset.y);
        }

    }

    @Override
    public void onClick() {
        System.out.println("button click");
        animator.playState("click");
    }
}
