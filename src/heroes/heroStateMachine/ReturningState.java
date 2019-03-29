package heroes.heroStateMachine;

import heroes.Hero;

public class ReturningState implements HeroState {

    private Hero.HeroProxy hp;
    public ReturningState (Hero.HeroProxy hp){
        this.hp = hp;
    }

    @Override
    public void executeLoop() {
        System.out.println("returning");
        hp.followRoute();
    }

    @Override
    public void giveOrder(String s) {

    }
}
