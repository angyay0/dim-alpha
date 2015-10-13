//abstractg->item->Pixel
package org.aimos.abstractg.physics;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import org.aimos.abstractg.core.Launcher;
import org.aimos.abstractg.handlers.Constants;

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
	public enum CTYPE {
		GOLD(100, "coinc"),
        RED(50, "coinb"),
        BLUE(1, "coina");// arrglar create body // arreglar nombre

		private int value;
        private String spriteSrc;

		CTYPE(int val, String src){
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
	public CTYPE type;

	public Coin(CTYPE type, World world, Vector2 pos){
        super(world);
		this.type = type;
        TextureAtlas atlas = Launcher.res.getAtlas("coins");
        sprite = atlas.findRegion(type.getSrc());
        initBody(pos);
	}

	public int getValue(){
		return type.getValue();
	}

    public static Array<Coin> generateCoins(World w, Vector2 pos, long val){
        Array<Coin> coins = new Array<Coin>();
        long res;
        for(CTYPE c : CTYPE.values()){
            res = val % c.getValue();
            for(int i = 0; i < val / c.getValue(); i++){
                coins.add(new Coin(c, w, pos));
            }
            val = res;
        }
        return coins;
    }

    public static Coin generateCoin(World w, Vector2 pos, long val){
        return new Coin(CTYPE.BLUE,w,pos);
    }

    public static Array<Coin> generateCoins(World w, Array<Vector2> points, long val){
        //sprint 3
        return null;
    }

    @Override
    protected final void createBody(Vector2 pos) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(pos.x / Constants.PTM, pos.y / Constants.PTM);
        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox((getWidth() / 2) / Constants.PTM, (getHeight() / 2) / Constants.PTM);

        FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;
        fdef.density = 1;
        fdef.friction = 1f;
        fdef.filter.categoryBits = Constants.BIT.ITEM.BIT();
        fdef.filter.maskBits = (short) (Constants.BIT.FLOOR.BIT() | Constants.BIT.CHARACTER.BIT());
        fdef.restitution = 0.2f;

        // create character collision box fixture
        body.createFixture(fdef).setUserData(Constants.DATA.PICKUP);
        shape.dispose();
    }
}
