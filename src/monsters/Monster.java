package monsters;

import heroes.Buff;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

public class Monster {
    Monster(){}

    int strength;
    String type;

    // Prizes
    List<Buff> prizeBuffs;

    public List<Buff> getPrizeBuffs() {
        return prizeBuffs;
    }
    //

    // Bad impact
    List<Buff> defubbs;

    public List<Buff> getDefubbs() {
        return defubbs;
    }
    //


    public String getType() {
        return type;
    }

    public int getStrength() {
        return strength;
    }
}