package org.aimos.abstractg.physics;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import org.aimos.abstractg.character.*;
import org.aimos.abstractg.character.Character;

/**
 * Created by EinarGretch on 02/10/2015.
 */
public class MeleeWeapon extends Weapon {

    public MeleeWeapon(long bd, float m, long v, World w) {
        super(bd, m, v, w);
    }

    @Override
    protected void attackMotion() {

    }

    @Override
    protected final void createBody(Vector2 pos) {

    }

}
