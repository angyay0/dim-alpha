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

    /*public static final short BIT_CHARACTER = 2;
    public static final short BIT_FLOOR = 4;
    public static final short BIT_WALL = 8;
    public static final short BIT_DECO = 16;
    public static final short BIT_GRANADE = 32;
    public static final short BIT_BULLET = 64;*/

    public static enum BIT{
        CHARACTER((short)2), FLOOR((short)4), WALL((short)8), DECO((short)16), GRANADE((short)32),
        BULLET((short)64), ITEM((short)128), SWORD((short)256);

        private short bit;

        BIT(short b){
            bit = b;
        }

        public short BIT(){
            return bit;
        }
    }

    public static enum DATA {
        BODY, FOOT, HEAD, ATTACK, CELL, INTERACTIVE, PICKUP, WEAPON, GRANADE, EXPLOSION, BULLET;
    };


}
