// abstractg->item->Weapon
package org.aimos.abstractg.physics;

import org.aimos.abstractg.character.Enemy;
import org.aimos.abstractg.character.Player;

/**
 * Clase generadora de las armas de las cuales puede ser utilizada por los jugadores y enemigos
 *
 * @author EinarGretch
 * @version 1.0.0
 * @date 07/09/2015
 * @company Aimos Studio
 */

public abstract class Weapon extends Item {

    //El dano extra que hace
    private long bonusDamage;
    //El multiplicador de dano
    private float multiplier;
    //El valor del arma (Precio)
    private long value;
    //Dueño del arma
    private Character owner;

    /**
     * Default Constructor for Weapon
     * @param bd bonus damage
     * @param m multiplier
     * @param v value
     */
    public Weapon(long bd, float m, long v){
        bonusDamage = bd;
        multiplier = m;
        value = v;
        owner = null;
    }

    /**
     * Sets owner of the weapon
     * @param chara
     */
    public void setOwner(Character chara){
        owner = chara;
    }

    /**
     * Gets Owner of the weapon
     * @return Character
     */
    public Character getOwner(){
        return owner;
    }

    /**
     * obtener el daño del arma sumado con el daño del personaje
     *
     * @return long
     * @params type player
     */
    public long getWeaponDamage() {

        return 0;
    }

    /**
     * Attack method for weapon
     * @return
     */
    public boolean attack() {
        if (owner == null) return false;
        return subAttack();
    }

    //Specialized Attack Method
    protected abstract boolean subAttack();

}
