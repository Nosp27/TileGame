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

    public Rectangle getRect() {
        return rect;
    }

    public Image getImage() {
        return image;
    }

    protected Sprite(String imagePath){
        try{
            image = ImageIO.read(new File(imagePath));
            rect = new Rectangle(image.getWidth(), image.getHeight());
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    void draw(Graphics g, int x, int y, int w, int h){
        rect = new Rectangle(x,y,w,h);
        g.drawImage(image, x, y, w, h, null);
    }

    Boolean checkClick(Point mouseCords){
        return rect.contains(mouseCords);
    }
}
