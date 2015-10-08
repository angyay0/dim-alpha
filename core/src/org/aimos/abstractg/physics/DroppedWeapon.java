package org.aimos.abstractg.physics;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by EinarGretch on 06/10/2015.
 */
public class DroppedWeapon extends Item implements PickUp{

    private Weapon weapon;

    public DroppedWeapon(Weapon w, Vector2 pos){
        weapon = w;
        sprite = w.getSprite();
        createBody(pos);
    }

    @Override
    public void render(SpriteBatch sb) {
        weapon.render(sb);
    }

    @Override
    protected final void createBody(Vector2 pos) {

    }

    public Weapon getWeapon() {
        return weapon;
    }
}
