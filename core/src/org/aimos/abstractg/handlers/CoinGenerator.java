package org.aimos.abstractg.handlers;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import org.aimos.abstractg.handlers.factory.Generator;
import org.aimos.abstractg.physics.Coin;

/**
 * Created by Angyay0 on 13/10/2015.
 */

public class CoinGenerator implements Generator<Coin> {

    @Override
    public Array<Coin> generateObjects() {
        return null;
    }

    @Override
    public Array<Coin> generateObjects(long amount) {
        return null;
    }

    @Override
    public Array<Coin> generateObjectsBox2D(World w, Array<Vector2> poss) {
        return null;
    }

    @Override
    public Array<Coin> generateObjectsBox2D(World w, Array<Vector2> poss, long amount) {
        return null;
    }

    @Override
    public Array<Coin> generateObjectBox2D(World w, Vector2 pos) {
        return null;
    }

    @Override
    public Array<Coin> generateObjectBox2D(World w, Vector2 pos, long val) {
        Array<Coin> coins = new Array<Coin>();
        long res;
        for(Coin.CTYPE c : Coin.CTYPE.values()){
            res = val % c.getValue();
            for(int i = 0; i < val / c.getValue(); i++){
                coins.add(new Coin(c, w, pos));
            }
            val = res;
        }
        return coins;
    }
}
