package render;

import java.awt.image.BufferedImage;

public abstract class SpriteAnimator {
    protected Sprite animated;
    protected Runnable refreshListener;

    protected SpriteAnimator(Sprite animated, Runnable refresh) {
        this.animated = animated;
        refreshListener = refresh;
    }

    protected void setPath(String s){
        animated.readImage(s);
    }
}
