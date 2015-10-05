//abstractg->character->NPC
package org.aimos.abstractg.character;

import com.badlogic.gdx.physics.box2d.World;

/**
 * Representa el personaje no jugable
 * puede relizar acciones mediante un script en LUA
 *
 * @version 1.0.3
 * @date 07/09/2015
 * @updated 14/09/2015
 * @author Anngyay0,Gretch
 * @company AIMOS Studio
 *
 **/

 public class NPC extends Character {

 	/**
 	 * @param name del personaje Type String
	 * @param spriteSrc del personaje Type String
	 * @param world del personaje Type String
 	 **/
 	 public NPC(String spriteSrc, String name, World world,int x,int y) {
		 super(spriteSrc, name, world, x, y);
	 }

 	 /**
 	  * Metodo para cargar el behavior del NPC
 	  * desde un script en LUA
 	  *
 	  * @param file del archivo Type String
 	  **/
 	 public	boolean loadScript(String file){

		return false;
 	 }

	@Override
	public void createBodyExtra(float x, float y) {

	}

	@Override
	public void setAnimations() {

	}
}
