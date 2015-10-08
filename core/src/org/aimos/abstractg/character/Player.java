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
 * @author Angyay0, Gretch
 * @version 1.0.3
 * @date 07/09/2015
 * @updated 14/09/2015
 * @company AIMOS Studio
 **/

public class Player extends Character {

    private Array<Weapon> weapons;
    private long enemiesKilled = 0;

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
    }

    @Override
    protected final void createBodyExtra(Vector2 pos) {

    }

    @Override
    protected final void setExtraAnimations() {

    }

    public void addMoney(Coin c) {
        money += c.getValue();
        c.dispose();
    }

    public void addWeapon(DroppedWeapon w) {
        /*if(w.getWeapon() instanceof MeleeWeapon){

        }else if(w.getWeapon() instanceof ThrowWeapon){

        }else if(w.getWeapon() instanceof ShootWeapon){

        }*/
        weapons.add(w.getWeapon());
        setWeapon(w.getWeapon());
        w.dispose();
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
}
