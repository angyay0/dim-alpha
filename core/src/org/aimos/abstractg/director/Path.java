package org.aimos.abstractg.director;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Angyay0 on 27/10/2015.
 */
public class Path {
    //
    public List<Path> prev;
    //
    public List<Path> next;
    //
    public float mixerRatio = 0.75f; /*0.75f - 0.96f*/

    /**
     *
     */
    public Path(){;}

    /**
     *
     * @param nxtpool
     * @param prvpool
     */
    public Path(int nxtpool, int prvpool){
        this();
        prev = new ArrayList<Path>(prvpool);
        next = new ArrayList<Path>(nxtpool);
    }

    /**
     *
     * @param node
     * @return
     */
    public boolean addNode(Node node){

        return false;
    }

    /**
     *
     * @return
     */
    public boolean generatePath(){

        return false;
    }
}
