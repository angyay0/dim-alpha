package org.aimos.abstractg.handlers;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import org.aimos.abstractg.character.Boss;
import org.aimos.abstractg.character.Enemy;
import org.aimos.abstractg.gamestate.Play;
import org.aimos.abstractg.handlers.factory.Generator;

/**
 * Created by Alumno on 30/10/2015.
 */
public class EnemyGenerator implements Generator<Enemy> {
    @Override
    public Array<Enemy> generateObjects() {
        return null;
    }

    @Override
    public Array<Enemy> generateObjects(long amount) {
        return null;
    }

    @Override
    public Array<Enemy> generateObjectsBox2D(Play p, Array<Vector2> poss) {
        return null;
    }

    @Override
    public Array<Enemy> generateObjectsBox2D(Play p, Array<Vector2> poss, long amount) {
        return null;
    }

    @Override
    public Array<Enemy> generateObjectBox2D(Play p, Vector2 pos) {
        return null;
    }

    @Override
    public Array<Enemy> generateObjectBox2D(Play p, Vector2 pos, long amount) {
        Enemy ene = new Enemy("player","Minion", p, pos/*.set(getPosition().x,getPosition().y+getPosition().y)*/);
        ene.setConfigurations("minion.lua", 20,0);
        return null;
    }
}
