package heroes;

import map.locations.Location;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Hero {
    int x,y;
    String pathName;

   //accessors
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getPathName() {
        return pathName;
    }

    public Integer getSin(MortalSins sinType) {
        return sins.getOrDefault(sinType, 0);
    }

    public Integer setSin(MortalSins sinType, Integer value) {
        if (value < 0 || value > 100)
            throw new IllegalArgumentException();
        return sins.put(sinType, value);
    }

    //mortal sins
    public enum MortalSins {
        ANGER,
        ENVY,
        PRIDE,
        AVARICE,//алчность
        LUST,
        GLUTTONY
    }

    private Map<MortalSins, Integer> sins;
    //

    //personal qualities
    //TODO: personal qualities
    //


    //constructor
    public Hero() {
        sins = new HashMap<>(6);
    }

    //public methods
    public final Location preferLocation(List<Location> variants) {
        Float bestChoiceCoeff = 0f;
        Location bestChoice = null;
        Map<Location, Float> map = calculatePreferedLocation(variants);
        for (Location l : map.keySet()) {
            if (bestChoice == null || map.get(l) > bestChoiceCoeff) {
                bestChoice = l;
                bestChoiceCoeff = map.get(l);
            }
        }
        return bestChoice;
    }

    public int getStrength(){
        //TODO: implement strength count
        throw new NotImplementedException();
    }

    //protected
    //return map for
    protected Map<Location, Float> calculatePreferedLocation(List<Location> variants) {
        //TODO: implement preferences
        throw new NotImplementedException();
    }
}
