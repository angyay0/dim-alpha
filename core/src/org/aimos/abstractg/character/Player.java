//Player
package org.aimos.abstractg.character;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
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
    private int coinCount = 0;

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
        setStats(400,10);
     //   chainShape = true;
    //    loadScript("ia_agents.lua");
       // loadScript();
    //    setSelfToScript();

      //  Thread t = new Thread(this);
    //    t.start();
    }

    @Override
    protected final void createBodyExtra(Vector2 pos) {
        for (Fixture fixture : getBody().getFixtureList()) {
            Filter f = fixture.getFilterData();
            f.groupIndex = (short) (Constants.BIT.CHARACTER.BIT() | Constants.BIT.PLAYER.BIT());
        }
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
            act();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(count < 100){
                count++;
            }else{
                running = false;
            }

        }
    }

    public void addMoney(Coin c) {
        addMoney(c.getValue());
        coinCount++;
    }

    public void addWeapon(DroppedWeapon w) {
        weapons.add(w.getWeapon());
        setWeapon(w.getWeapon());
    }

    public boolean hasMinimumCoins(int min){
        return (coinCount>=min);
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

    public void setWinMapLevel(){ getPlay().setWin(true);   }

}
