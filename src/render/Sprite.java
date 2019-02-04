package render;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class Sprite {
    protected BufferedImage image;
    private Rectangle rect;
    public int order;

    public Rectangle getRect() {
        return rect;
    }

    public Image getImage() {
        return image;
    }

    protected Sprite(String imagePath){
        order = 0;
        if(imagePath != null)
        readImage(imagePath);
    }

    protected Sprite(String imagePath, int order){
        this(imagePath);
        this.order = order;
    }

    public void draw(Graphics g, int x, int y, int w, int h){
        draw(g, x, y, w, h, null);
    }

    public void draw(Graphics g, int x, int y, int w, int h, Rectangle viewRect){
        if(w == 0)
            w = image.getWidth();
        if(h == 0)
            h = image.getHeight();

        rect = new Rectangle(x,y,w,h);

        if(viewRect == null || rect.intersects(viewRect))
        g.drawImage(image, x, y, w, h, null);
    }

    Boolean checkClick(Point mouseCords){
        return rect.contains(mouseCords);
    }

    public void onClick(){

    }

    void readImage(String path){
        try{
            image = ImageIO.read(new File(path));
            rect = new Rectangle(image.getWidth(), image.getHeight());
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
