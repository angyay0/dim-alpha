package org.aimos.abstractg.handlers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;

/**
 * Created by EinarGretch on 17/09/2015.
 */
public class Animation {
    //Almacena los sprites del personaje
    private AtlasRegion[] frames;
    //Tiempo de actualizacion
    private float time;
    //delta del juego
    private float delta;
    //animacion del personaje
    private int currentFrame;
    //variable para hacer los efectos de caminar
    private int timesPlayed;

    /**
     * Metodo constructor e inicializacion de algunas variables.
     * @param frames
     * @param delta
     */
    public Animation(Array<AtlasRegion> frames, float delta) {
        this.frames = frames.toArray(AtlasRegion.class);
        this.delta = delta;
        time = 0;
        currentFrame = 0;
    }

    /**
     *
     * @param f
     */
    public void setDelta(float f) {
        delta = f;
    }

    /**
     *
     * @param i
     */
    public void setFrame(int i) {
        if(i < frames.length) currentFrame = i;
    }

    /**
     *
     * @param frames
     * @param delay
     */
    public void setFrames(AtlasRegion[] frames, float delay) {
        this.frames = frames;
        time = 0;
        currentFrame = 0;
        timesPlayed = 0;
        this.delta = delay;
    }

    /**
     * actualizacion de graficos
     * @param dt
     */
    public void update(float dt) {
        if(delta <= 0) return;
        time += dt;
        while(time >= delta) {
            step();
        }
    }

    /**
     * Visualiza los frames del personaje
     */
    private void step() {
        time -= delta;
        currentFrame++;
        if(currentFrame == frames.length) {
            currentFrame = 0;
            timesPlayed++;
        }
    }

    /**
     * Busca el frame del personaje
     * @return frame especifico
     */
    public AtlasRegion getFrame() {
        return frames[currentFrame];
    }

    /**
     *
     * @return
     */
    public int getTimesPlayed() {
        return timesPlayed;
    }

    /**
     *
     * @return
     */
    public boolean hasPlayedOnce() {
        return timesPlayed > 0;
    }

}