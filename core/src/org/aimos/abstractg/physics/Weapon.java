// abstractg->item->Weapon
package org.aimos.abstractg.physics;

import org.aimos.abstractg.character.Enemy;
import org.aimos.abstractg.character.Player;

/**
 * Clase generadora de las armas de las cuales puede ser utilizada por los jugadores y enemigos
 *
 * @author Anonymous
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
    //Dueño
    private Character owner;

    public Weapon(Character o) {
        owner = o;
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
