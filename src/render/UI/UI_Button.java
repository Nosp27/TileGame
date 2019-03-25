package render.UI;

import java.awt.*;
import java.util.LinkedList;

public class UI_Button extends UI_Sprite implements Child {
    private String text = "sas"; //text displayed on btn

    Point textOffset = new Point(3, 11);

    ButtonAnimator animator; // animator for rendering click reaction

    Runnable onClickInternal; // on click settings for builder

    public LinkedList<Runnable> listenerList = new LinkedList<>();

    /**
     * Set text to display on button
     * @param text - text to display
     */
    public void setText(String text) {
        this.text = text;
    }


    /**
     * Get text displayed on button
     * @return String with text on button
     */
    public String getText() {
        return text;
    }


    /**
     * Button constructor
     * @param path - path to the Sprite file
     * @param clickedPath - path to clicked Sprite file
     */
    UI_Button(String path, String clickedPath) {
        super(path);
        animator = new ButtonAnimator(path,this);
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
        onClickInternal.run();
    }
}
