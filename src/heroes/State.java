package heroes;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public enum State {
    IDLE(State.WALKING),
    WALKING(State.RETURNING, State.SEARCHING, State.FIGHT_ENGAGE),
    SEARCHING(State.WALKING),
    FIGHT_ENGAGE(State.FIGHT),
    FIGHT(State.RETREAT, State.WALKING, State.RETURNING),
    RETREAT(State.RETURNING, State.WALKING),
    RETURNING(State.IDLE, State.FIGHT, State.SEARCHING);

    List<State> next;
    State previous;

    State(State... next) {
        this.next = new LinkedList<>(Arrays.asList(next));
    }

    public State getPrevious() {
        return previous;
    }
}
