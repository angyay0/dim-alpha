//abstractg->character->Enemy
package org.aimos.abstractg.character;

import com.badlogic.gdx.physics.box2d.World;

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
	protected Enemy(String spriteSrc, String name, World world,int x,int y) {
		super(spriteSrc, name, world, x, y);
	}

	/**
	 * Metodo para cargar la IA del personaje
	 *
	 * @param file nombre del archivo Type String
	 **/
	public void loadAIScript(String file){

	}

	/**
	 * Metodo para atacar
	 **/
	public void attack(){

	}

	/**
	 * Metodo para defenderse
	 **/
	public void defend(){

	}

	@Override
	public void createBodyExtra(float x, float y) {

	}

	@Override
	public void setAnimations() {

	}
}
