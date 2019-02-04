package render;

import java.awt.image.BufferedImage;

public abstract class SpriteAnimator {
    protected Sprite animated;

    protected SpriteAnimator(Sprite animated) {
        this.animated = animated;
    }

    private void setPath(String s){
        animated.readImage(s);
    }

    protected void setFrame(String s){
        setPath(s);
    }
}
