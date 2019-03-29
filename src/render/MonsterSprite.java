package render;

import monsters.Monster;
import monsters.MonsterListener;

import java.awt.*;

public class MonsterSprite extends Sprite implements MonsterListener {
    private static final String deadMonster = "res/monsters/dead.png";
    private Monster monster;

    public MonsterSprite(Monster m) {
        super(m.getPath(), 90);
        monster = m;
        m.addListener(this);
    }

    @Override
    public void onClick() {

    }

    public Monster getMonster() {
        return monster;
    }

    @Override
    public void draw(Graphics g, int x, int y, int w, int h) {

        super.draw(g, x, y, w, h);
    }

    @Override
    public void onKill(Monster m) {
        readImage(deadMonster);
    }
}
