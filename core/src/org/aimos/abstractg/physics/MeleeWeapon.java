package org.aimos.abstractg.physics;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import org.aimos.abstractg.character.*;
import org.aimos.abstractg.character.Character;

/**
 * Created by EinarGretch on 02/10/2015.
 */
public class MeleeWeapon extends Weapon {

    public MeleeWeapon(long bd, float m, long v) {
        super(bd, m, v);
    }

    @Override
    protected void attackMotion() {

    }

    @Override
    protected final void createBody(Vector2 pos) {

    }

}
