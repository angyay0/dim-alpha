// abstractg->item->Weapon
package org.aimos.abstractg.physics;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;

import org.aimos.abstractg.character.Character;
import org.aimos.abstractg.core.Launcher;

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
    protected long bonusDamage;
    //El multiplicador de dano
    protected float multiplier;
    //El valor del arma (Precio)
    protected long value;
    //Dueño del arma
    protected Character owner;
    //Union con el dueño
    protected Joint joint;

    /**
     * Default Constructor for Weapon
     * @param bd bonus damage
     * @param m multiplier
     * @param v value
     */
    public Weapon(long bd, float m, long v, World w, String spriteSrc){
        super(w);
        bonusDamage = bd;
        multiplier = m;
        value = v;
        owner = null;
        TextureAtlas atlas = Launcher.res.getAtlas("armas");
        sprite = atlas.createSprite(spriteSrc);
        //sprite.set
    }

    /**
     * Sets owner of the weapon
     * @param chara
     */
    public void setOwner(Character chara){
        owner = chara;
        initBody(chara.getPosition().cpy());
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
    public void attack() {
        if (owner == null) return;
        attackMotion();
    }

    public long damage(Character c) {
        long d = (long) ((owner.getAttack() + bonusDamage) * multiplier);
        c.damage(d);
        return d;
    }

    //Initiate weapon attack motion
    protected abstract void attackMotion();


    @Override
    protected abstract void createBody(Vector2 pos);
}
