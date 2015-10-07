//abstractg->item->Item
package org.aimos.abstractg.physics;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.physics.box2d.Body;

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

    protected AtlasRegion sprite;

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(sprite, getX() * Constants.PTM - (getWidth() / 2), getY() * Constants.PTM - (getHeight() / 2));
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

}