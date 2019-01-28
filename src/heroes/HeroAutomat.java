package heroes;

import java.util.*;

public class HeroAutomat {
    private State currentState;
    private Hero controlledHero;

    public State getCurrentState() {
        return currentState;
    }

    public void transit(State s) {
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

    public void doSomething() {
        switch (currentState) {
            case IDLE:
                break;
            case WALKING:
                controlledHero.currentLocation.message_heroCame(controlledHero);
        }
    }
}
