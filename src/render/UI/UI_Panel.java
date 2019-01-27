package render.UI;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class UI_Panel extends UI_Sprite implements Parent, Child {
    private Parent parent;
    private List<Child> children;

    public UI_Panel(String path) {
        super(path);
        children = new LinkedList<>();
    }

    @Override
    public void draw(Graphics g, int x, int y, int w, int h) {
        if (!isVisible)
            return;

        x = ui_x - x;
        y += ui_y;

        super.draw(g, x, y, w, h);
    }

    @Override
    public List<Child> getChildren() {
        return children;
    }

    @Override
    public void addChild(Child c) {
        c.setParent(this);
        children.add(c);
    }

    @Override
    public void plant(int x, int y){
        super.plant(x,y);
        for(Child c : children)
            ((UI_Sprite)c).plant(x,y);
    }

    @Override
    public void hide(){
        super.hide();
        for(Child c : children)
            ((UI_Sprite)c).hide();
    }

    @Override
    public Parent getParent() {
        return parent;
    }

    @Override
    public void setParent(Parent parent) {
        this.parent = parent;
    }
}
