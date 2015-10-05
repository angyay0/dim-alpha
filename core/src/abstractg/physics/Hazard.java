//abstractg->item->Hazard
package abstractg.physics;

import abstractg.character.Player;

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

 public class Hazard extends Item {

	//Lista para especificar el tipo de efectos
	public enum EFFECT_TYPE {
		EXPLOSION,
		POISON,
		PARALYSIS,
		NONE
	};

	//Indica el tipo de efecto del peligro
	public EFFECT_TYPE type;
	//Indica el dano que hara al jugador
	private long damage;
	//Indica el score ganado al superarlo(SI aplica)
	public long score;

	public Hazard(float posx,float posy){
		super(posx,posy);
	}

	/**
	 * Usado para calcular el dano del peligro al jugador
	 *
	 * @param player tipo Player para calcular dano
	 **/
	public void inflictHazard(Player player){

		//TO IMPLEMENT
	}
 }
