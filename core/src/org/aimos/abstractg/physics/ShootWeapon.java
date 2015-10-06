package org.aimos.abstractg.physics;

/**
 * Created by EinarGretch on 02/10/2015.
 */
public class ShootWeapon extends Weapon {

    /**
     * Default Constructor for Weapon
     *
     * @param bd bonus damage
     * @param m  multiplier
     * @param v  value
     */
    public ShootWeapon(long bd, float m, long v) {
        super(bd, m, v);
    }

    @Override
    protected boolean subAttack() {
        return false;
    }
}
