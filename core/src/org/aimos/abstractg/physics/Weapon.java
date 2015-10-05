// abstractg->item->Weapon
package org.aimos.abstractg.physics;

import org.aimos.abstractg.character.Enemy;
import org.aimos.abstractg.character.Player;

/**
 *	Clase generadora de las armas de las cuales puede ser utilizada por los jugadores y enemigos
 *
 *	@version 1.0.0
 *	@date 07/09/2015
 *	@author Anonymous
 *	@company Aimos Studio
 *
*/

public class Weapon extends Item {

	//Lista para describir el tipo de arma
	public enum WEAPON_TYPE {
				FIREGUN,
				MEELEE,
				OBJECT,
				FUTURE,
				TRADITIONAL,
				NONE
	};

	//El dano extra que hace
	private long bonusDamage;
	//El multiplicador de dano
	private float multiplier;
	//Define el tipo del arma
	private WEAPON_TYPE type;
	//El valor del arma (Precio)
	private long value;

	public Weapon(WEAPON_TYPE type,float posX,float posY){
		super(posX,posY);
		this.type = type;
	}

	/**
	*	obtener el daño del arma sumado con el daño del jugador
	*	@params type player
	*	@return long
	*/
	public long getWeaponDamage(Player player){

		return 0;
	}
	/**
	*	@params type enemy
	*	@return long
	*/
	public long getWeaponDamage(Enemy enemy){

		return 0;
	}

}
