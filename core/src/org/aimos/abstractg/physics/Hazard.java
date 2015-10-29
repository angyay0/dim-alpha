//abstractg->item->Hazard
package org.aimos.abstractg.physics;

import com.badlogic.gdx.math.Vector2;

import org.aimos.abstractg.character.Player;
import org.aimos.abstractg.gamestate.Play;

/**
 * Clase que representa objetos de peligro
 * como rocas, sierras, explosiones
 *
 * @version 1.0.0
 * @date 07/09/2015
 * @author Angyay0
 * @company AIMOS STUDIO
 *
 **/

 public class Hazard extends Item implements Interactive{

	//Lista para especificar el tipo de efectos
	public enum TYPE {
		EXPLOSION,
		POISON,
		PARALYSIS,
		NONE
	};

	//Indica el tipo de efecto del peligro
	public TYPE type;
	//Indica el dano que hara al jugador
	private long damage;
	//Indica el score ganado al superarlo(SI aplica)
	public long score;

	public Hazard(Play p) {
		super(p);
	}
	/**
	 * Usado para calcular el dano del peligro al jugador
	 *
	 * @param player tipo Player para calcular dano
	 **/
	public void inflictHazard(Player player){

		//TO IMPLEMENT
	}
	@Override
	public void interact() {

	}

	@Override
	protected final void createBody(Vector2 pos) {

	}
 }
