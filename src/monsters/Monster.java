package monsters;

import heroes.Buff;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.LinkedList;
import java.util.List;

public class Monster {

    private List<MonsterListener> listeners = new LinkedList<>();

    private boolean dead;

    public void kill(){
        dead = true;
        for(MonsterListener l : listeners)
            l.onKill(this);
    }

    public boolean isDead(){
        return dead;
    }

    String path;
    public String getPath(){
        return path;
    }

    Monster(){
        prizeBuffs = new LinkedList<>();
        defubbs = new LinkedList<>();
    }

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

    public void addListener(MonsterListener listener){
        listeners.add(listener);
    }
}