package heroes;

public class HeroAutomat {
    private HeroState currentState;

    public HeroState getCurrentState() {
        return currentState;
    }

    void transit(HeroState s) {
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
        ha.currentState = HeroState.IDLE;
        return ha;
    }
}
