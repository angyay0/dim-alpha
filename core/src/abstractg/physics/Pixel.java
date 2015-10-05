//abstractg->item->Pixel
package abstractg.physics;

/**
 * Clase que representa el objeto pixel
 * que representa la moneda del juego
 *
 * @version 1.0.0
 * @date 07/09/2015
 * @author Angyay0
 * @company AIMOS STUDIO
 *
 **/

public class Pixel extends Item {

	//Lista para especificar el tipo de pixel y valor
	public enum PIXEL_TYPE {
		BLUE(1),
		RED(50),
		GOLD(100);

		private int value;

		PIXEL_TYPE(int val){
			value = val;
		}
		public int getValue(){ return value; }

	};

	//Tipo del pixel
	public PIXEL_TYPE type = PIXEL_TYPE.BLUE;

	public Pixel(PIXEL_TYPE type,float px,float py){
		super(px,py);
		this.type = type;

	}

	public int getPixelValue(){
		return type.getValue();
	}
}
