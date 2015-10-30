package org.aimos.abstractg.director;

/**
 * Created by EinarGretch on 28/10/2015.
 */
public class Arc {

    private Node origin, destination;
    //private int distance;

    public Arc(Node o, Node d/*, int dis*/){
        origin = o;
        destination = d;
        //distance = dis;
        destination.addArc(this);
    }

    /*public int getDistance(){
        return distance;
    }*/

    public Node getDestination(){
        return destination;
    }

    public Node getOrigin(){
        return origin;
    }

    public Node getOther(Node n){
        if(n.equals(destination)) return origin;
        if(n.equals(origin)) return destination;
        return null;
    }

    public boolean isRecursive(){
        return (origin.equals(destination));
    }
}
