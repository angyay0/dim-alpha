package org.aimos.abstractg.physics;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import org.aimos.abstractg.gamestate.Play;

/**
 * Created by EinarGretch on 06/10/2015.
 */
public class DroppedWeapon extends Item implements PickUp{

    private Weapon weapon;

    public DroppedWeapon(Weapon w, Play play, Vector2 pos){
        super(play);
        weapon = w;
        setSprite(w.getSprite());
        initBody(pos);
    }

    @Override
    public void draw(SpriteBatch sb) {
        weapon.draw(sb);
    }

    @Override
    protected final void createBody(Vector2 pos) {

    }

    public Weapon getWeapon() {
        return weapon;
    }
}
