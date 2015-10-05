//Player
package abstractg.character;

import static abstractg.handlers.AimosVars.PTM;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import abstractg.handlers.AimosVars;
import abstractg.handlers.Animation;

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
    private static final String RUN_SEQ = "corre";

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
    public void createBodyExtra(float x, float y) {
        // create box shape for player foot
        PolygonShape shape = new PolygonShape();
        shape.setAsBox((to2DBoxSize(getWidth() / 2)) / PTM, (to2DBoxSize(getHeight() / 4)) / PTM , new Vector2(0, (-to2DBoxSize(getHeight())) / PTM), 0);

        // create fixturedef for player foot
        FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;
        fdef.isSensor = true;
        fdef.filter.categoryBits = AimosVars.BIT_CHARACTER;
        fdef.filter.maskBits = AimosVars.BIT_FLOOR;
        body.createFixture(fdef).setUserData("foot");

        //create fixturedef for player head
        shape.setAsBox((to2DBoxSize(getWidth() / 2)) / PTM, (to2DBoxSize(getHeight() / 4)) / PTM , new Vector2(0, (to2DBoxSize(getHeight())) / PTM), 0);
        fdef.shape = shape;
        fdef.isSensor = true;
        fdef.filter.categoryBits = AimosVars.BIT_CHARACTER;
        fdef.filter.maskBits = AimosVars.BIT_WALL | AimosVars.BIT_FLOOR;
        body.createFixture(fdef).setUserData("head");

        //create fixturedef for player shoulders
        /*shape.setAsBox((to2DBoxSize(getWidth() * 1.1f)) / PTM, (to2DBoxSize(getHeight() / 4)) / PTM );
        fdef.shape = shape;
        fdef.isSensor = true;
        fdef.filter.categoryBits = AimosVars.BIT_CHARACTER;
        fdef.filter.maskBits = AimosVars.BIT_WALL;
        body.createFixture(fdef).setUserData("shoulders");

        //create fixturedef for player attack zone
        shape.setAsBox((to2DBoxSize(getWidth() * 1.4f)) / PTM, (to2DBoxSize(getHeight())) / PTM );
        fdef.shape = shape;
        fdef.isSensor = true;
        fdef.filter.categoryBits = AimosVars.BIT_CHARACTER;
        fdef.filter.maskBits = AimosVars.BIT_CHARACTER;
        body.createFixture(fdef).setUserData("attack");*/

        //dispose shape
        shape.dispose();
    }

    @Override
    public void setAnimations() {
        animations.add(new Animation(atlas.findRegions(RUN_SEQ), 1 / 5f));
    }

    /**
     * En este metodo se define la logica
     * del ataque del jugador hacia el enemigo
     *
     * @params enemy Type Enemy
     **/
    public void attack(Enemy enemy) {

    }

    /**
     * En este metodo se define la interaccion
     * que el usuario tiene con los objectos y otros personajes
     * del escenario
     **/
    public void interact() {

    }

    /**
     * En este metodo se define la logica del modo defensa del jugador
     **/
    public void defend() {

    }
}
