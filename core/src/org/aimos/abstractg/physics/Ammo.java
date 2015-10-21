package org.aimos.abstractg.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by EinarGretch on 21/10/2015.
 */
public abstract class Ammo extends Item implements Pool.Poolable, Runnable{

    private Weapon weapon;

    public Ammo(World w, Weapon sw) {
        super(w);
        weapon = sw;
        setSprite();
    }

    protected abstract void setSprite();

    protected abstract void extraDispose();

    @Override
    public abstract void run();

    @Override
    protected abstract void createBody(Vector2 pos);

    public Weapon getWeapon(){
        return weapon;
    }

    @Override
    public void reset() {
        super.dispose();
        extraDispose();
    }

    @Override
    public void dispose() {
        weapon.getPool().free(this);
    }
}
