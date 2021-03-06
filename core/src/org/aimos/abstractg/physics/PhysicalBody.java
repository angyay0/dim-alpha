package org.aimos.abstractg.physics;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

import org.aimos.abstractg.gamestate.Play;

/**
 * Created by EinarGretch on 06/10/2015.
 */
public abstract class PhysicalBody {

    private Play play = null;
    private Body body = null;
    private boolean visible = true;

    protected PhysicalBody(Play p){
        play = p;
    }

    public Body getBody(){
        return body;
    }

    public boolean hasBody(){
        return (body != null);
    }

    public Play getPlay() {
        return play;
    }


    protected boolean createBody(BodyDef bdef){
        if(body == null) {
            body = play.getWorld().createBody(bdef);
            return true;
        }
        return false;
    }

    public void dispose(){
        if(hasBody()) {
            visible = false;
            play.getWorld().destroyBody(body);
            body = null;
        }
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

    public boolean isVisible(){
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
        if(hasBody()) return;
        createBody(pos);
    }

    public void draw(SpriteBatch sb){
        if(isVisible()) render(sb);
    }

    public World getWorld(){
        return play.getWorld();
    }

    protected abstract void render(SpriteBatch sb);

    public abstract int getWidth();

    public abstract int getHeight();

    protected abstract void createBody(Vector2 pos);
}
