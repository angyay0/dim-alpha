//abstractg->item->Pixel
package org.aimos.abstractg.physics;

/**
 * Clase que representa el objeto pixel
 * que representa la moneda del juego
 *
 * @version 1.0.0
 * @date 07/09/2015
 * @author EinarGretch, Angyay0
 * @company AIMOS STUDIO
 *
 **/

public class Coin extends Item {

	//Lista para especificar el tipo de pixel y valor
	public enum COIN_TYPE {
		BLUE(1),
		RED(50),
		GOLD(100);

		private int value;

		COIN_TYPE(int val){
			value = val;
		}
		public int getValue(){ return value; }

	};

	//Tipo del pixel
	public COIN_TYPE type = COIN_TYPE.BLUE;

	public Coin(COIN_TYPE type, float px, float py){
		this.type = type;

	}

	public int getPixelValue(){
		return type.getValue();
	}
}
