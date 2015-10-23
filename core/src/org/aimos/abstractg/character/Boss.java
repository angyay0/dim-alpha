//Boss
package org.aimos.abstractg.character;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import org.aimos.abstractg.gamestate.Play;

/**
 * Clase que define al jefe(Enemigo)
 *
 * @version 1.0.3
 * @date 07/09/2015
 * @updated 14/09/2015
 * @author Gretch,Angyay0
 * @company AIMOS Studio
 **/

public class Boss extends Enemy {

	// defense: se va a usar para definir el nivel de defensa
	private long defense;
	//bonusDamage: aqui se va a definir el daño extra que podra causar el jefe
	private long bonusDamage;
	//phases: se va a usar para definir las fases que tendra el jefe
	private int phases;

	/**
	 * @param spriteSrc
	 * @param name
	 * @param play
	 * @param pos
	 */
	public Boss(String spriteSrc, String name, Play play, Vector2 pos) {
		super(spriteSrc, name, play, pos);
	}


	/**
	 *
	 * Este metodo funciona para revivir al jefe
	 *
	 **/
	public void revive(){
	}
	/**
	 *
	 * Este metodo funciona para crear enemigos de apoyo para el jefe y
	 *retorna una variable tipo Enemy
	 *@return Type Enemy
	 **/
	public Enemy createEnemy(){
		return new Enemy("cambia esto luego","Minion",getPlay(), getPosition().cpy());
	}



}
