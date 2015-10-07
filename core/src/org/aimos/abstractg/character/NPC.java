//abstractg->character->NPC
package org.aimos.abstractg.character;

import com.badlogic.gdx.physics.box2d.World;

import org.aimos.abstractg.physics.Interactive;

/**
 * Representa el personaje no jugable
 * puede relizar acciones mediante un script en LUA
 *
 * @version 1.0.3
 * @date 07/09/2015
 * @updated 14/09/2015
 * @author EinarGretch,Angyay0
 * @company AIMOS Studio
 *
 **/

 public class NPC extends Character implements Interactive{

 	/**
 	 * @param name del personaje Type String
	 * @param spriteSrc del personaje Type String
	 * @param world del personaje Type String
 	 **/
 	 public NPC(String spriteSrc, String name, World world,int x,int y) {
		 super(spriteSrc, name, world, x, y);
	 }

	@Override
	protected final void createBodyExtra(float x, float y) {

	}

	@Override
	protected final void setExtraAnimations() {

	}

	@Override
	public void interact() {

	}
}
