package heroes.heroStateMachine;

import heroes.Hero;
import javafx.util.Pair;
import map.locations.EventType;
import monsters.Monster;

public class WalkingState implements HeroState {

    private Hero.HeroProxy hp;
    public WalkingState(Hero.HeroProxy hp) {
        this.hp = hp;
    }

    @Override
    public void executeLoop() {
        System.out.println("walk");
        hp.followRoute();
        Pair<EventType, Object> event = hp.getCurrentLocation().message_heroCame(hp.getHero());
        switch (event.getKey()) {
            case MONSTER:
                hp.startFight((Monster) event.getValue());
                break;
            case TREASURE:
                hp.setState(hp.getHero().getSearchingState());
                //TODO:grab item
                break;
            case NONE:
                break;
        }
    }

    @Override
    public void giveOrder(String s) {

    }
}
