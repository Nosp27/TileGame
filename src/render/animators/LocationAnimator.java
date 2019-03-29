package render.animators;

import javafx.util.Pair;
import render.Sprite;
import render.animators.SpriteAnimator;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class LocationAnimator extends SpriteAnimator {
    boolean isPlaying;
    final List<Pair<String, Long>> frames;
    volatile int frameIndex = 0;

    private class AnimatorTask {
        final Runnable frameSet = this::fs;
        volatile Long offset;
        volatile Long counter;

        AnimatorTask(Long offset) {
            this.offset = offset;
            counter = 0L;
        }

        synchronized void fs() {
            counter = setNextFrame();
        }
    }

    private static final Vector<AnimatorTask> tasks = new Vector<>();

    static Thread playTimer = new Thread(() -> {
        try {
            long interval = 20;

            while (true) {
                synchronized (tasks) {
                    boolean b = false;
                    for (AnimatorTask task : tasks) {
                        if (task.offset > 0) {
                            task.offset -= interval;
                            continue;
                        }

                        if (task.counter <= 0) {
                            task.frameSet.run();
                        } else {
                            task.counter -= interval;
                            if (!b) {
                                //System.out.println(task.counter);
                                b = true;
                            }
                        }

                    }
                }
                Thread.sleep(interval);
            }
        } catch (InterruptedException ignore) {

        }
    });

    public LocationAnimator(Sprite animated) {
        super(animated);
        //TODO: location animator
        frames = new LinkedList<>();
        isPlaying = false;

        if (!playTimer.isAlive())
            playTimer.start();
    }

    public void addFrame(String path, long delay) {
        frames.add(new Pair<>(path, delay));
    }

    public void play() {
        if (isPlaying) {
            return;
        }

        synchronized (tasks) {
            long delay = ThreadLocalRandom.current().nextLong(2000);
            tasks.add(new AnimatorTask(delay));
        }

    }

    private synchronized long setNextFrame() {
        Pair<String, Long> frame = frames.get(frameIndex);
        setFrame(frame.getKey());
        frameIndex = (frameIndex + 1) % frames.size();
        return frame.getValue();
    }
}
