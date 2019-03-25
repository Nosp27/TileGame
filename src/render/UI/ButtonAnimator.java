package render.UI;

import render.Sprite;
import render.SpriteAnimator;

import java.util.*;

public class ButtonAnimator extends SpriteAnimator {

    private Map<String, State> states;

    private String stateName;

    public ButtonAnimator(String idlePath, Sprite animated){
        super(animated);
        states = new HashMap<>();
        states.put("idle", new State(idlePath, 0));
    }

    public void addState(String name, String path, int delay){
        states.put(name, new State(path, delay));
    }

    public void playState(String name){
        if(!states.containsKey(name))
            return;

        State current = states.get(name);
        stateName = name;

        setFrame(current.path);

        if(current.delay_ms > 0){
            Timer t = new Timer();
            t.schedule(new TimerTask() {
                @Override
                public void run() {
                    setFrame(states.get("idle").path);
                }
            }, current.delay_ms);
        }
    }

    class State{
        public State(String p, int d){
            path = p;
            delay_ms = d;
        }
        public String path;
        public int delay_ms;
    }

    public String getStateName(){
        return stateName;
    }
}
