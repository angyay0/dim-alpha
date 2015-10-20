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

    public PhysicalBody(World w){
        world = w;
    }

    public Body getBody(){
        return body;
    }

    public boolean hasBody(){
        return (body != null);
    }

    public boolean setBody(Body body){
        if(body != null) {
            this.body = body;
            return true;
        }
        return false;
    }

    public void dispose(){
        visible = false;
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

    public void setPosition(Vector2 pos){
        if(body == null )return;
        body.getPosition().set(pos);
    }

    public float getX(){
        if(body == null) return -1;
        return body.getPosition().x;
    }

    public float getY(){
        if(body == null) return -1;
        return body.getPosition().y;
    }

    public Vector2 getPosition() {
        if(body == null) return null;
        return body.getPosition();
    }

    public void initBody(Vector2 pos){
        visible = true;
        if()
        createBody(pos);
    }

    public abstract void render(SpriteBatch sb);

    public abstract int getWidth();

    public abstract int getHeight();

    protected abstract void createBody(Vector2 pos);
}
