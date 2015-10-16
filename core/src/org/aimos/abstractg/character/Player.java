//Player
package org.aimos.abstractg.character;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

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

    public volatile boolean running = true;
    int count = 0;

    /**
     * Creates a new character
     *
     * @param spriteSrc
     * @param name
     * @param world
     * @param pos
     */
    public Player(String spriteSrc, String name, World world, Vector2 pos) {
        super(spriteSrc, name, world, pos);
        weapons = new Array<Weapon>();
     //   chainShape = true;
     //   loadScript();
      //  setSelfToScript();

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
    public void setSelfToScript() {
        iaChunk.setCharacter(this);
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
        money += c.getValue();
    }

    public void addWeapon(DroppedWeapon w) {
        /*if(w.getWeapon() instanceof MeleeWeapon){

        }else if(w.getWeapon() instanceof ThrowWeapon){

        }else if(w.getWeapon() instanceof ShootWeapon){

        }*/
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

    public long getMoney(){
        return money;
    }
    public Array<Weapon> getWeapons(){
        return weapons;
    }

    public long getEnemiesKilled() {
        return enemiesKilled;
    }
}
