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
    //Almacena los arcos entre nodos
    private Array<Arc> arcs;

    /**
     * tipos de item en el juego
     */
    public enum TYPE{
        BLANK, PLATFORM, ENEMY_GENERATOR, ITEM_GENERATOR, COIN_GENERATOR,
        INTERACTIVE_SPOT, INTEREST_SPOT, PICKUP
    }
    //Variable para seleccionar el item
    private TYPE type;

    //Variable para la prioridad
    private int priority;

    /**
     * metodo constructor de la clase
     * @param p parametro de Play
     * @param t Tipo
     */
    public Node(Play p, TYPE t){
        super(p);
        type = t;
        arcs = new Array<Arc>();
    }

    /**
     * Metodo constructor
     * @param p Play
     * @param t Tipo
     * @param a arcos entre nodos
     */
    public Node(Play p, TYPE t, Array<Arc> a){
        super(p);
        type = t;
        arcs = a;
    }

    /**
     * Agrega arcos
     * @param a
     */
    public void addArc(Arc a){
        arcs.add(a);
    }

    /**
     * Obtiene arco
     * @param a
     * @return
     */
    public Node getOtherEnd(Arc a){
        return a.getOther(this);
    }

    /**
     * Obtiene arco
     * @param i
     * @return
     */
    public Node getOtherEnd(int i){
        return arcs.get(i).getOther(this);
    }

    /**
     * Obtiene total de arcos
     * @return
     */
    public int getArcNum(){
        return arcs.size;
    }

    /**
     * Obtiene el tipo
     * @return
     */
    public TYPE getType(){return type;}

    /**
     * Crea el cuerpo
     * @param pos
     */
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
