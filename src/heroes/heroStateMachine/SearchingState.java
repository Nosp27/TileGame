package heroes.heroStateMachine;

import heroes.Hero;
import javafx.util.Pair;
import map.locations.EventType;
import monsters.Monster;

public class SearchingState implements HeroState {

    private Hero.HeroProxy hp;
    public SearchingState(Hero.HeroProxy hp) {
        this.hp = hp;
    }

    @Override
    public void executeLoop() {
        System.out.println("searching");
        hp.setPreviousState();
    }

    @Override
    public void giveOrder(String s) {

    }
}
