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
        if (body == null){
            Gdx.app.debug("NO ITEM","EIT");
            return;
        }
        sprite.setRotation(MathUtils.radiansToDegrees * body.getAngle());
        sprite.setPosition((getX() * Constants.PTM) - (getWidth() / 2), (getY() * Constants.PTM) - (getHeight() / 2));
        sb.begin();
        sprite.draw(sb);
      /*  if( resized != null ){

            sb.draw(sprite, getX() * Constants.PTM - (getWidth() / 2), getY() * Constants.PTM - (getHeight() / 2), resized.width, resized.height); //Corregir
        }else {*/
            //sb.draw(sprite, getX() * Constants.PTM - (getWidth() / 2), getY() * Constants.PTM - (getHeight() / 2)); //Corregir
        //sb.draw(sprite, getX() * Constants.PTM - (getWidth() / 2), getY() * Constants.PTM - (getHeight() / 2), sprite.getOriginX(), sprite.getOriginY(), getWidth(), getHeight(), 1, 1,sprite.getRotation()); //Corregir
       /* }**/

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
