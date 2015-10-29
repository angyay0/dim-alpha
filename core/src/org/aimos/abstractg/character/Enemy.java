//abstractg->character->Enemy
package org.aimos.abstractg.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import org.aimos.abstractg.gamestate.Play;
import org.aimos.abstractg.physics.Coin;
import org.aimos.abstractg.physics.DroppedWeapon;

/**
 * Representa el personaje enemigo generico
 * acciones e ia mediante un script en LUA
 *
 * @version 1.0.3
 * @date 07/09/2015
 * @updated 14/09/2015
 * @author EinarGretch,Angyay0
 * @company AIMOS STUDIO
 *
 **/

 public class Enemy extends Character {

	public static volatile boolean running = true;

    /**
     * @param spriteSrc
     * @param name
     * @param play
     * @param pos
     */
    public Enemy(String spriteSrc, String name, Play play, Vector2 pos) {
        super(spriteSrc, name, play, pos);
		loadScript("officer.lua");
		setSelfToScript();
		setStats(8,1);
    }

    @Override
	protected void createBodyExtra(Vector2 pos) {

	}

	@Override
	protected void setExtraAnimations() {

	}

	@Override
	public void die() {
		if (hasWeapon()) dropWeapon();
        //Coin.generateCoins(getPlay(), getPosition(), getMoney());
        getPlay().remove(this);
//		Gdx.app.exit();

	}

	public DroppedWeapon dropWeapon(){
		return new DroppedWeapon(getWeapon(), getPlay(), getPosition().cpy());
	}

	@Override
	public void run() {

	}

}
