//abstractg->item->Item
package abstractg.physics;

/**
 * Clase abstracta para manejar los items en el juego
 *
 * @version 1.1.0
 * @date 07/09/2015
 * @author Angyay0
 * @company AIMOS STUDIO
 *
 **/

public abstract class Item {

    //Posicion X en el mapa
    protected float TILE_X;
    //Posicion Y en el mapa
    protected float TILE_Y;

    /**
     *  Crear un nuevo objeto colocando las posiciones del objeto
     *  en el escenario
     *
     * @param posX
     * @param posY
     */
    protected Item(float posX, float posY){
        TILE_X = posX;
        TILE_Y = posY;
    }

}
