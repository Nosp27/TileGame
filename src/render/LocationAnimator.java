package render;

import javafx.util.Pair;

import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

public class LocationAnimator extends SpriteAnimator {
    boolean isPlaying;
    List<Pair<String, Long>> frames;

    static Timer playTimer = new Timer("location animator timer");

    protected LocationAnimator(Sprite animated) {
        super(animated);
        //TODO: location animator
        frames = new LinkedList<>();
        isPlaying = false;
    }

    public void addFrame(String path, long delay){
        frames.add(new Pair<>(path, delay));
    }

    public void play(){
        if(isPlaying){
            return;
        }
        long delay = ThreadLocalRandom.current().nextLong(700);
        for(Pair<String, Long> frame : frames){
            playTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    setFrame(frame.getKey());
                }
            }, delay, frame.getValue());
            delay += frame.getValue();
        }
    }
}
