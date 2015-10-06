package org.aimos.abstractg.physics;

/**
 * Created by EinarGretch on 02/10/2015.
 */
public class ThrowWeapon extends Weapon {

    public ThrowWeapon(Character o) {
        super(o);
    }

    @Override
    protected boolean subAttack() {
        return false;
    }
}
