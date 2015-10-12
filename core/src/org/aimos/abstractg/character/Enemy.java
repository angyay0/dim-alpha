//abstractg->character->Enemy
package org.aimos.abstractg.character;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import org.aimos.abstractg.physics.DroppedWeapon;

/**
 * Representa el personaje enemigo generico
 * acciones e ia mediante un script en LUA
 *
 * @version 1.0.3
 * @date 07/09/2015
 * @updated 14/09/2015
 * @author Angyay0,Gretch
 * @company AIMOS STUDIO
 *
 **/

 public class Enemy extends Character {

	//Atributo de ataque base del personaje
	public long baseAtk;
	//Atributo indicador de puntos de salud
	public long hp;
	//Indicador del puntaje que se otorgan al derrotarlo
	public long score;
	//Cantidad de pixeles que se otorgan al derrotarlo
	public int pixels;

	/**
	 * Creates a new character
	 *
	 * @param spriteSrc
	 * @param name
	 * @param world
	 */
	protected Enemy(String spriteSrc, String name, World world, Vector2 pos) {
		super(spriteSrc, name, world, pos);
	}

	@Override
	protected void createBodyExtra(Vector2 pos) {

	}

	@Override
	protected void setExtraAnimations() {

	}

	@Override
	public void setSelfToScript() {//correct with avstract factory
		iaChunk.setCharacter(this);
	}

	public DroppedWeapon dropWeapon(){
		return new DroppedWeapon(weapon, world, getPosition().cpy());
	}

	@Override
	public void run() {

	}

}
