package org.aimos.abstractg.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import org.aimos.abstractg.gamestate.Play;

/**
 * Created by EinarGretch on 07/10/2015.
 */
public class Gem extends Item implements PickUp{


    public Gem(Play p) {
        super(p);
    }

    @Override
    protected void createBody(Vector2 pos) {

    }
}
