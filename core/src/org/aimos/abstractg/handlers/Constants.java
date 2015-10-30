package org.aimos.abstractg.handlers;

/**
 * Created by EinarGretch on 17/09/2015.
 */
public class Constants {

    //Pixel to Meter ratio
    public static final float PTM = 100f;

    //LOAD RATIO PER THREAD
    public static final float LRPT = 0.80f;

    //CONCURRENTMAP CAPACITY
    public static final int CCAPACITY = 100;

    public enum BIT{
        CHARACTER((short)2), FLOOR((short)4), WALL((short)8), DECO((short)16), GRANADE((short)32),
        BULLET((short)64), ITEM((short)128), SWORD((short)256), COIN((short)512), PLAYER((short)1024);

        private short bit;

        BIT(short b){
            bit = b;
        }

        public short BIT(){
            return bit;
        }
    }

    public enum DATA {
        BODY, FOOT, HEAD, ATTACK, CELL, INTERACTIVE, PICKUP, GRANADE, EXPLOSION, BULLET
    }

    public enum STATE{
        SPLASH(0),MENU(1),WORLD_SELECT(2),SOLO_PLAY(3),LEVEL_SELECT(4),MULTI_PLAY(5),PAUSE(6),
        GAME_OVER(7),OPTIONS(8);

        private int id = -1;

        STATE(int id){ this.id = id; }

        public int getID(){ return id;  }
        public void setID(int id){  this.id = id;   }
    }

}
