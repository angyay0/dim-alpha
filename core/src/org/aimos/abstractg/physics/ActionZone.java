package org.aimos.abstractg.physics;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by EinarGretch on 06/10/2015.
 */
public class ActionZone extends PhysicalBody implements Interactive{

    public ActionZone(World w) {
        super(w);
    }

    @Override
    public void render(SpriteBatch sb) {

    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    protected final void createBody(Vector2 pos) {

    }

    @Override
    public void interact() {

    }
}
