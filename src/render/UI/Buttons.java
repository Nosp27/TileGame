package render.UI;

import java.awt.*;

/**
 * Builder for different buttons
 */
public class Buttons {
    private String idlePath,
            clickedPath;

    ButtonState state;
    private int delay = 70;

    public Buttons setState(ButtonState state) {
        this.state = state;
        return this;
    }

    public Buttons setDelay(int _delay) {
        this.delay = _delay;
        return this;
    }

    public Buttons setIdlePath(String path) {
        idlePath = path;
        return this;
    }

    public Buttons setClickedPath(String path) {
        clickedPath = path;
        return this;
    }

    public UI_Button build() {
        checkCorrectBuild();

        UI_Button button = new UI_Button(idlePath, clickedPath);

        switch (state) {
            case ONE_SHOT:
                button.animator.addState("click", clickedPath, delay);
                button.onClickInternal = () -> button.animator.playState("click");
                break;
            case CHECK_UNCHECK:
                button.animator.addState("click", clickedPath, 0);
                button.animator.addState("unclick", idlePath, 0);

                button.onClickInternal = () -> {
                    if (!"click".equals(button.animator.getStateName()))
                        button.animator.playState("click");
                    else button.animator.playState("unclick");
                };
                break;
        }

        return button;
    }

    private void checkCorrectBuild() {
        if (idlePath == null && clickedPath == null)
            throw new RuntimeException("idle path and clicked path not set");

        if (idlePath == null)
            throw new RuntimeException("idle path not set");

        if (clickedPath == null)
            throw new RuntimeException("clicked path not set");

        if (state == null) {
            throw new RuntimeException("state not set");
        }
    }

    public enum ButtonState {
        ONE_SHOT,
        CHECK_UNCHECK
    }
}


