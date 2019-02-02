package heroes;

import java.util.List;

public enum HeroState {
    IDLE,
    WALKING,
    SEARCHING,
    FIGHT_ENGAGE,
    FIGHT,
    RETREAT,
    RETURNING;

    List<HeroState> next;
    HeroState previous;

    public HeroState getPrevious() {
        return previous;
    }
}
