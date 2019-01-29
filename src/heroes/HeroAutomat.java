package heroes;

public class HeroAutomat {
    private State currentState;

    public State getCurrentState() {
        return currentState;
    }

    void transit(State s) {
        if (!currentState.next.contains(s))
            throw new IllegalArgumentException();

        s.previous = currentState;
        currentState = s;
    }

    void transitBack() {
        if(currentState.previous == null)
            throw new IllegalArgumentException();

        currentState = currentState.previous;
    }


    private HeroAutomat() {
    }

    public static HeroAutomat generateAutomat() {
        HeroAutomat ha = new HeroAutomat();
        ha.currentState = State.IDLE;
        return ha;
    }
}
