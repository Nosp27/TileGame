package render;

import heroes.Hero;

import java.awt.*;

public class HeroSprite extends Sprite {
    private Hero hero;

    public HeroSprite(Hero h) {
        super(h.getPathName(), 100);
        hero = h;
    }

    @Override
    public void onClick() {

    }

    public heroes.Hero getHero() {
        return hero;
    }
}
