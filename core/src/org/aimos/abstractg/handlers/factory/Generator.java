package org.aimos.abstractg.handlers.factory;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Angyay0 on 13/10/2015.
 */
public interface Generator<T> {

    public Array<T> generateObjects();

    public Array<T> generateObjects(long amount);

    public Array<T> generateObjectsBox2D(World w,Array<Vector2> poss);

    public Array<T> generateObjectsBox2D(World w,Array<Vector2> poss,long amount);

    public Array<T> generateObjectBox2D(World w,Vector2 pos);

    public Array<T> generateObjectBox2D(World w,Vector2 pos,long amount);

}
