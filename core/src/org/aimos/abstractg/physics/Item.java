//abstractg->item->Item
package org.aimos.abstractg.physics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import org.aimos.abstractg.handlers.Constants;

/**
 * Clase abstracta para manejar los items en el juego
 *
 * @version 1.1.0
 * @date 07/09/2015
 * @author EinarGretech,Angyay0
 * @company AIMOS STUDIO
 *
 **/

public abstract class Item extends PhysicalBody{

    protected Sprite sprite;

    public Item(World w) {
        super(w);
    }

    public Sprite getSprite() {
        return sprite;
    }

    @Override
    public void render(SpriteBatch sb) {
        sprite.setRotation(MathUtils.radiansToDegrees * body.getAngle());
        sprite.setPosition((getX() * Constants.PTM) - (getWidth() / 2), (getY() * Constants.PTM) - (getHeight() / 2));
        sb.begin();
        sprite.draw(sb);
        sb.end();
    }

    @Override
    public int getWidth() {
        return sprite.getRegionWidth();
    }

    @Override
    public int getHeight() {
        return sprite.getRegionHeight();
    }

    @Override
    protected abstract void createBody(Vector2 pos);
}
