package render.UI;

import render.Sprite;
import render.SpriteAnimator;

import java.util.*;

public class ButtonAnimator extends SpriteAnimator {

    Map<String, State> states;

    public ButtonAnimator(String idlePath, Runnable refresh, Sprite animated){
        super(animated, refresh);
        states = new HashMap<>();
        states.put("idle", new State(idlePath, 0));
    }

    private void refresh(){
        refreshListener.run();
    }

    public void addState(String name, String path, int delay){
        states.put(name, new State(path, delay));
    }

    public void playState(String name){
        if(!states.containsKey(name))
            return;

        State s = states.get(name);

        setPath(s.path);
        refresh();

        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                setPath(states.get("idle").path);
                refresh();
            }
        }, s.delay_ms);
    }

    class State{
        public State(String p, int d){
            path = p;
            delay_ms = d;
        }
        public String path;
        public int delay_ms;
    }
}
