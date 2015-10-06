//Player
package org.aimos.abstractg.character;

import com.badlogic.gdx.physics.box2d.World;

import org.aimos.abstractg.handlers.Animation;

/**
 * Clase que define al jugador
 *
 * @author Angyay0, Gretch
 * @version 1.0.3
 * @date 07/09/2015
 * @updated 14/09/2015
 * @company AIMOS Studio
 **/

public class Player extends Character {

    //Definicion de Variables para el ATLAS
    private static final String RUN_SEQ = "run"; // 5
    // variable para definir el ataque basico del jugador
    private long baseAtk;
    // variable para definir el score del jugador
    private long score;
    // variable para definir los pixeles del jugador
    private long pixels;
    //variable para definir la vida del jugadoe
    private long hp;
    //Variabl for controlling jump limit
    private static final int MAX_JUMPS = 2;
    //variable which controls jump amout
    private int jump = 0;

    /**
     * Creates a new character
     *
     * @param spriteSrc
     * @param name
     * @param world
     * @param x         pos
     * @param y
     */
    public Player(String spriteSrc, String name, World world, float x, float y) {
        super(spriteSrc, name, world, x, y);
    }

    @Override
    protected final void createBodyExtra(float x, float y) {

    }

    @Override
    protected final void setExtraAnimations() {
        animations.add(new Animation(atlas.findRegions(RUN_SEQ), ANIMATION_DELTA));
    }

}
