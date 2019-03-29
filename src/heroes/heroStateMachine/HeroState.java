package heroes.heroStateMachine;

import heroes.Hero.HeroProxy;

public interface HeroState {
    void executeLoop();
    void giveOrder(String s);
}
