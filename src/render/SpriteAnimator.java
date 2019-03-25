package render;

public abstract class SpriteAnimator {
    protected Sprite animated;

    protected SpriteAnimator(Sprite animated) {
        this.animated = animated;
    }

    protected void setFrame(String s){
        animated.readImage(s);
    }
}
