package heroes;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public enum State {
    IDLE,
    WALKING,
    SEARCHING,
    FIGHT_ENGAGE,
    FIGHT,
    RETREAT,
    RETURNING;

    List<State> next;
    State previous;

    public State getPrevious() {
        return previous;
    }
}
