package org.aimos.abstractg.physics;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import org.aimos.abstractg.director.Arc;
import org.aimos.abstractg.gamestate.Play;

/**
 * Created by EinarGretch on 06/10/2015.
 */
public class ActionZone extends PhysicalBody implements Interactive{

    private Arc arc;

    public ActionZone(Play p) {
        super(p);
        arc = null;
    }

    public void setArc(Arc a){
        arc = a;
    }

    @Override
    public void render(SpriteBatch sb) {

    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    protected final void createBody(Vector2 pos) {

    }

    @Override
    public void interact() {
  /*
        if (Intersector.overlaps(, getBoundingRectangle())) {
        }
*/
    }
}
