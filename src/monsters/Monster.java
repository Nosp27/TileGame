package monsters;

import heroes.Buff;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

public class Monster {
    //TODO: implement monster

    String type;

    // Prizes
    List<Buff> prizeBuffs;
    //

    // Bad impact
    List<Buff> defubbs;
    //

    public String getType() {
        return type;
    }

    public int getStrength(){
        throw new NotImplementedException();
    }
}