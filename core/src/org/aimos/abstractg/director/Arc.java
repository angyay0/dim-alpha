package org.aimos.abstractg.director;

/**
 * Created by EinarGretch on 28/10/2015.
 */
public class Arc {
    //Variable direcciones de nodo
    private Node origin, destination;
    //private int distance;

    /**
     * metodo constructor
     * @param o origen
     * @param d destino
     */
    public Arc(Node o, Node d/*, int dis*/){
        origin = o;
        destination = d;
        //distance = dis;
        destination.addArc(this);
    }

    /**
     * @return destino
     */
    public Node getDestination(){
        return destination;
    }

    /**
     * @return origen
     */
    public Node getOrigin(){
        return origin;
    }

    /**
     * busca nodo
     * @param n nodo
     * @return retorna origen, destino o nulo
     */
    public Node getOther(Node n){
        if(n.equals(destination)) return origin;
        if(n.equals(origin)) return destination;
        return null;
    }

    /**
     * busca recursividad
     * @return boolean
     */
    public boolean isRecursive(){
        return (origin.equals(destination));
    }
}
