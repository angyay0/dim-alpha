package org.aimos.abstractg.handlers.director;

/**
 * Created by Angyay0 on 27/10/2015.
 */

public class Node {

    public enum TYPE{
        PLATFORM(1), ENEMY_GENERATOR(2), ITEM_GENERATOR(3), COIN_GENERATOR(4),
        INTERACTIVE_SPOT(5), INTEREST_SPOT(6), BLANK(0);

        private int val;
        TYPE(int v){
            val =v;
        }

        public int getValue(){return val;}
    };

    private TYPE type;

    private boolean forward = false;

    private boolean backward = false;

    private boolean root = false;

    public Node(){

    }

    public Node(TYPE t){
        type = t;
    }

    public Node(TYPE t,boolean f,boolean b){
        this(t);
        forward = f;
        backward = b;
    }

    public TYPE getType(){return type;}
    public void setRootNode(boolean bol){   root = bol; }
    public void setForward(boolean bol){    forward = bol;  }
    public void setBackward(boolean bol){   backward = bol; }
    public boolean moveForward(){   return forward;     }
    public boolean moveBackward(){  return backward;    }
    public boolean isRootNode(){    return root;    }

}
