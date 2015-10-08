package org.aimos.abstractg.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by EinarGretch on 07/10/2015.
 */
public class Gem extends Item implements PickUp{

    public Gem(World w) {
        super(w);
    }

    @Override
    protected void createBody(Vector2 pos) {

    }
}
