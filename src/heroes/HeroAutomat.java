package heroes;

import javafx.util.Pair;
import map.locations.EventType;
import monsters.Monster;

public class HeroAutomat {
    private State currentState;
    private Hero controlledHero;

    public State getCurrentState() {
        return currentState;
    }

    void transit(State s) {
        if (!currentState.next.contains(s))
            throw new IllegalArgumentException();

        currentState = s;
    }

    private HeroAutomat() {
    }

    public static HeroAutomat generateAutomat(Hero h) {
        HeroAutomat ha = new HeroAutomat();
        ha.currentState = State.IDLE;
        ha.controlledHero = h;
        return ha;
    }
}
