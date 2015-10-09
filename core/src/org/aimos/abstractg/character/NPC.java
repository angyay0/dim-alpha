//abstractg->character->NPC
package org.aimos.abstractg.character;

import com.badlogic.gdx.math.Vector2;
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
 	 public NPC(String spriteSrc, String name, World world, Vector2 pos) {
		 super(spriteSrc, name, world, pos);
	 }

	@Override
	protected final void createBodyExtra(Vector2 pos) {

	}

	@Override
	protected final void setExtraAnimations() {

	}

	@Override
	public void setSelfToScript() {

	}

	@Override
	public void interact() {

	}
}
