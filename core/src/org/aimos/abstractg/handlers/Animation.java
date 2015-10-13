package org.aimos.abstractg.handlers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;

/**
 * Created by EinarGretch on 17/09/2015.
 */
public class Animation {

    private AtlasRegion[] frames;
    private float time;
    private float delta;
    private int currentFrame;

    private int timesPlayed;

    public Animation(Array<AtlasRegion> frames, float delta) {
        this.frames = frames.toArray(AtlasRegion.class);
        this.delta = delta;
        time = 0;
        currentFrame = 0;
    }


    public void setDelta(float f) {
        delta = f;
    }

    public void setFrame(int i) {
        if(i < frames.length) currentFrame = i;
    }

    public void setFrames(AtlasRegion[] frames, float delay) {
        this.frames = frames;
        time = 0;
        currentFrame = 0;
        timesPlayed = 0;
        this.delta = delay;
    }

    public void update(float dt) {
        if(delta <= 0) return;
        time += dt;
        while(time >= delta) {
            step();
        }
    }

    private void step() {
        time -= delta;
        currentFrame++;
        if(currentFrame == frames.length) {
            currentFrame = 0;
            timesPlayed++;
        }
    }

    public AtlasRegion getFrame() {
        return frames[currentFrame];
    }
    public int getTimesPlayed() {
        return timesPlayed;
    }
    public boolean hasPlayedOnce() {
        return timesPlayed > 0;
    }

}