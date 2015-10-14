//abstractg->item->Item
package org.aimos.abstractg.physics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
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

    protected AtlasRegion sprite;

    public Item(World w) {
        super(w);
    }

    @Override
    public void render(SpriteBatch sb) {
        if (body == null){ Gdx.app.debug("NO ITEM","EIT");
            return;}
        sb.begin();
        sb.draw(sprite, getX() * Constants.PTM - (getWidth() / 2), getY() * Constants.PTM - (getHeight() / 2)); //Corregir
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

    public AtlasRegion getSprite() {
        return sprite;
    }
}
