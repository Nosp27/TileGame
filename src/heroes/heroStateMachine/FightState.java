package heroes.heroStateMachine;

import heroes.Hero;
import javafx.util.Pair;
import map.locations.EventType;
import monsters.Monster;

public class FightState implements HeroState {
    private Hero.HeroProxy hp;
    public FightState(Hero.HeroProxy hp) {
        this.hp = hp;
    }

    @Override
    public void executeLoop() {
        System.out.println("fight");
    }

    @Override
    public void giveOrder(String s) {

    }
}
