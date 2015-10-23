package org.aimos.abstractg.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

import org.aimos.abstractg.character.*;
import org.aimos.abstractg.character.Character;
import org.aimos.abstractg.gamestate.Play;

/**
 * Created by EinarGretch on 21/10/2015.
 */
public abstract class Ammo extends Item implements Pool.Poolable, Runnable{

    private Weapon weapon;

    protected Array<Character> targets;

    public Ammo(Play p, Weapon sw) {
        super(p);
        weapon = sw;
        targets = null;
        extraInit();
    }

    protected abstract void extraInit();

    protected abstract void extraDispose();

    @Override
    public abstract void run();

    @Override
    protected abstract void createBody(Vector2 pos);

    public Weapon getWeapon(){
        return weapon;
    }

    public Array<Character> getTargets(){
        return targets;
    }

    @Override
    public void reset() {
        super.dispose();
        unsetBody();
        extraDispose();
    }

    @Override
    public void dispose() {
        weapon.getPool().free(this);
    }
}
