package heroes.heroStateMachine;

import heroes.Hero;

public class IdleState implements HeroState {
    private Hero.HeroProxy hp;

    public IdleState(Hero.HeroProxy hp)
    {
        this.hp = hp;
    }

    @Override
    public void executeLoop() {

    }

    @Override
    public void giveOrder(String s) {
        hp.giveOrder(s);
    }
}
