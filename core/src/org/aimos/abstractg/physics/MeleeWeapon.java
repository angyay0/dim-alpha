package org.aimos.abstractg.physics;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by EinarGretch on 02/10/2015.
 */
public class MeleeWeapon extends Weapon {

    public MeleeWeapon(long bd, float m, long v) {
        super(bd, m, v);
    }

    @Override
    protected boolean subAttack() {
        return false;
    }

}
