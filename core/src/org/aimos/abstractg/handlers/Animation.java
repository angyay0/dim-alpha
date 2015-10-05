package org.aimos.abstractg.handlers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;

/**
 * Created by EinarGretch on 17/09/2015.
 */
public class Animation {

    private AtlasRegion[] frames;
    private float time;
    private float delay;
    private int currentFrame;

    private int timesPlayed;

    public Animation() {}

    public Animation(AtlasRegion[] frames) {

        this(frames, 1 / 12f);
    }

    public Animation(AtlasRegion[] frames, float delay) {
        this.frames = frames;
        this.delay = delay;
        time = 0;
        currentFrame = 0;
    }

    public Animation(Array<AtlasRegion> frames) {
        this(frames, 1 / 12f);
    }

    public Animation(Array<AtlasRegion> frames, float delay) {
        this.frames = frames.toArray(AtlasRegion.class);
        this.delay = delay;
        time = 0;
        currentFrame = 0;
    }


    public void setDelay(float f) { delay = f; }
    public void setCurrentFrame(int i) { if(i < frames.length) currentFrame = i; }
    public void setFrames(AtlasRegion[] frames) {
        setFrames(frames, 1 / 12f);
    }
    public void setFrames(AtlasRegion[] frames, float delay) {
        this.frames = frames;
        time = 0;
        currentFrame = 0;
        timesPlayed = 0;
        this.delay = delay;
    }

    public void update(float dt) {
        if(delay <= 0) return;
        time += dt;
        while(time >= delay) {
            step();
        }
    }

    private void step() {
        time -= delay;
        currentFrame++;
        if(currentFrame == frames.length) {
            currentFrame = 0;
            timesPlayed++;
        }
    }

    public AtlasRegion getFrame() {
        return frames[currentFrame];
    }

    public void flip(boolean x, boolean y){
        for (AtlasRegion frame : frames) {
            frame.flip(x,y);
        }
    }

    public int getTimesPlayed() {
        return timesPlayed;
    }
    public boolean hasPlayedOnce() {
        return timesPlayed > 0;
    }

}