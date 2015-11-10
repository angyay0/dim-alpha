//abstractg->character->NPC
package org.aimos.abstractg.character;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import org.aimos.abstractg.gamestate.Play;
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
	 * @param spriteSrc
	 * @param name
	 * @param play
	 * @param pos
	 */
	public NPC(String spriteSrc, String name, Play play, Vector2 pos) {
		super(spriteSrc, name, play, pos);
	}

	/**
	 * crea cuerpo
	 * @param pos
	 */
	@Override
	protected final void createBodyExtra(Vector2 pos) {

	}

	/**
	 * crea animacion
	 */
	@Override
	protected final void setExtraAnimations() {

	}

	/**
	 * elimina cuerpo si esta muerto
	 */
	@Override
	public void die() {
		dispose();
	}

	/**
	 * Hilo
	 */
	@Override
	public void run() {

	}

	/**
	 * Interact
	 */
	@Override
	public void interact() {

	}
}
