//abstractg->item->Pixel
package org.aimos.abstractg.physics;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import org.aimos.abstractg.core.Launcher;
import org.aimos.abstractg.gamestate.Play;
import org.aimos.abstractg.handlers.Constants;

import java.util.Random;

/**
 * Clase que representa el objeto Coin
 * que representa la moneda del juego
 *
 * @version 1.0.0
 * @date 07/09/2015
 * @author EinarGretch, Angyay0
 * @company AIMOS STUDIO
 *
 **/

public class Coin extends Item implements PickUp{

    //Lista para especificar el tipo de pixel y valor
	public enum TYPE {
		GOLD(100, "coinc"),
        RED(50, "coinb"),
        BLUE(1, "coina");// arrglar create body // arreglar nombre

		private int value;
        private String spriteSrc;

		TYPE(int val, String src){
			value = val;
            spriteSrc = src;
		}
		public int getValue(){
            return value;
        }
        public String getSrc(){
            return spriteSrc;
        }
	};

	//Tipo de moneda
	public TYPE type;

	public Coin(TYPE type, Play play, Vector2 pos){
        super(play);
		this.type = type;
        TextureAtlas atlas = Launcher.res.getAtlas("coins");
        setSprite(atlas.createSprite(type.getSrc()));
        initBody(pos);
	}

	public int getValue(){
		return type.getValue();
	}

    public static Array<Coin> generateCoins(Play p, Vector2 pos, long val){
        Array<Coin> coins = new Array<Coin>();
        long res;
        for(TYPE c : TYPE.values()){
            res = val % c.getValue();
            for(int i = 0; i < val / c.getValue(); i++){
                Vector2 nPos = pos.cpy();
                nPos.add(MathUtils.random(-2, 2),MathUtils.random(-2,2));
                coins.add(new Coin(c, p, nPos));
            }
            val = res;
        }
        return coins;
    }

    /**
     * La misma cantidad de monedas y vectores de posicion para que aparezcan
     * @param p
     * @param pos
     * @param amount
     * @return
     */
    public static Array<Coin> generateCoins(Play p, Vector2[] pos, int amount){
        Array<Coin> coins = new Array<Coin>();
        for(int i = 0; i < amount; i++){
            Vector2 nPos = pos[i].cpy();
            nPos.add(MathUtils.random(-2, 2),MathUtils.random(-2,2));
            coins.add(new Coin(TYPE.GOLD, p, nPos));
        }
        return coins;
    }

    public static Array<Coin> generateCoins(Play play, Vector2[] pos){
        Array<Coin> coins = new Array<Coin>();
        for (Vector2 p : pos) {
            coins.add(new Coin(TYPE.GOLD, play, p));
        }
        return coins;
    }

    @Override
    protected final void createBody(Vector2 pos) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(pos.x / Constants.PTM, pos.y / Constants.PTM);
        //bodyDef.fixedRotation = true;
        createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius((getWidth() / 2) / Constants.PTM);

        FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;
        fdef.density = 1;
        fdef.friction = 1f;
        fdef.filter.categoryBits = (short) (Constants.BIT.ITEM.BIT() | Constants.BIT.COIN.BIT());
        fdef.filter.maskBits = (short) (Constants.BIT.FLOOR.BIT() | Constants.BIT.PLAYER.BIT() | Constants.BIT.COIN.BIT()); //cambiar por player
        fdef.restitution = 0.4f;

        // create character collision box fixture
        getBody().createFixture(fdef).setUserData(Constants.DATA.PICKUP);
        shape.dispose();

        getBody().setUserData(this);
    }
}
