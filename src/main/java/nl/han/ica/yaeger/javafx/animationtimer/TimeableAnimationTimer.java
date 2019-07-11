package nl.han.ica.yaeger.javafx.animationtimer;

import javafx.animation.AnimationTimer;

/**
 * An {@link AnimationTimer} that calls its {@code handle()} method on the given interval, and not the default 60 times
 * per second.
 */
public abstract class TimeableAnimationTimer extends AnimationTimer {

    private long interval = 0;

    long prevTime = 0;

    /**
     * Creat a new {@code TimeableAnimationTimer} for the given interval time in milliseconds.
     *
     * @param interval the interval time in milliseconds
     */
    public TimeableAnimationTimer(long interval) {
        this.interval = interval * 1_000_000;
    }

    @Override
    public void handle(long now) {

        // some delay
        if ((now - prevTime) < interval) {
            return;
        }

        prevTime = now;

        handle();
    }

    /**
     * Method called whenever the interval time is reached
     */
    public abstract void handle();

}