package org.aimos.abstractg.physics;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by EinarGretch on 06/10/2015.
 */
public abstract class PhysicalBody {

    protected World world = null;
    protected Body body = null;
    protected boolean visible = true;

    public Body getBody(){
        return body;
    }

    public boolean setBody(Body body){
        if(body != null) {
            this.body = body;
            return true;
        }
        return false;
    }

    public void dispose(){
        world.destroyBody(body);
    }

    public void setVisibility(boolean visible){
        this.visible = visible;
    }

    public boolean getVisibility(){
        return visible;
    }

    public boolean flipVisibility(){
        visible = !visible;
        return visible;
    }

    public void setPosition(float x, float y){
        body.getPosition().x = x;
        body.getPosition().y = y;
    }

    public float getX(){
        return body.getPosition().x;
    }

    public float getY(){
        return body.getPosition().y;
    }

    public Vector2 getPosition() {
        return body.getPosition();
    }

    public abstract void render(SpriteBatch sb);

    public abstract int getWidth();

    public abstract int getHeight();
}
