//Player
package org.aimos.abstractg.character;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import org.aimos.abstractg.core.JsonIO;
import org.aimos.abstractg.gamestate.Play;
import org.aimos.abstractg.handlers.Constants;
import org.aimos.abstractg.physics.Coin;
import org.aimos.abstractg.physics.DroppedWeapon;
import org.aimos.abstractg.physics.Weapon;

/**
 * Clase que define al jugador
 *
 * @author EinarGretch, Angyay0
 * @version 1.0.3
 * @date 07/09/2015
 * @updated 14/09/2015
 * @company AIMOS Studio
 **/

public class Player extends Character {

    private Array<Weapon> weapons;
    private long enemiesKilled = 0;
    public boolean checkVelocityY = false;

    public volatile boolean running = true;
    int count = 0;

    /**
     * Creates a new character
     *
     * @param spriteSrc
     * @param name
     * @param play
     * @param pos
     */
    public Player(String spriteSrc, String name, Play play, Vector2 pos) {
        super(spriteSrc, name, play, pos);
        weapons = new Array<Weapon>();
        //setStats(200,1);
     //   chainShape = true;
    //    loadScript("ia_agents.lua");
       // loadScript();
    //    setSelfToScript();

      //  Thread t = new Thread(this);
    //    t.start();
    }

    @Override
    protected final void createBodyExtra(Vector2 pos) {

    }

    @Override
    protected final void setExtraAnimations() {

    }

    @Override
    public void die() {
        getPlay().remove(this);
    }

    @Override
    public void run() {
        while(running){
           // body.getLinearVelocity()
        }
    }

    public void addMoney(Coin c) {
        addMoney(c.getValue());
    }

    public void addWeapon(DroppedWeapon w) {
        weapons.add(w.getWeapon());
        setWeapon(w.getWeapon());
    }

    public void setWeapon(int i){
        setWeapon(weapons.get(i));
    }


    public void setWeapons(Array<Weapon> w){
        weapons = w;
    }

    public void addWeapon(Weapon w){
        weapons.add(w);
    }

    public Array<Weapon> getWeapons(){
        return weapons;
    }

    public long getEnemiesKilled() {
        return enemiesKilled;
    }

}
