package org.aimos.abstractg.director;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.utils.Array;

import org.aimos.abstractg.gamestate.Play;
import org.aimos.abstractg.physics.Item;

/**
 * Created by Angyay0 on 27/10/2015.
 */

public class Node extends Item {

    private Array<Arc> arcs;

    public enum TYPE{
        BLANK, PLATFORM, ENEMY_GENERATOR, ITEM_GENERATOR, COIN_GENERATOR,
        INTERACTIVE_SPOT, INTEREST_SPOT, PICKUP;
    };

    private TYPE type;

    private int priority;

    public Node(Play p, TYPE t){
        super(p);
        type = t;
        arcs = new Array<Arc>();
    }

    public Node(Play p, TYPE t, Array<Arc> a){
        super(p);
        type = t;
        arcs = a;
    }

    public void addArc(Arc a){
        arcs.add(a);
    }

    public Node getOtherEnd(Arc a){
        return a.getOther(this);
    }

    public Node getOtherEnd(int i){
        return arcs.get(i).getOther(this);
    }

    public int getArcNum(){
        return arcs.size;
    }

    public TYPE getType(){return type;}

    @Override
    protected void createBody(Vector2 pos) {
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set(pos.cpy());
        createBody(bdef);

        MassData m = getBody().getMassData();
        m.mass = 0;
        getBody().setMassData(m);
    }

}
