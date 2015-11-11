//Boss
package org.aimos.abstractg.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import org.aimos.abstractg.gamestate.Play;

/**
 * Clase que define al jefe(Enemigo)
 *
 * @version 1.0.3
 * @date 07/09/2015
 * @updated 14/09/2015
 * @author Gretch,Angyay0
 * @company AIMOS Studio
 **/

public class Boss extends Enemy {

	// defense: se va a usar para definir el nivel de defensa
	private long defense;
	//bonusDamage: aqui se va a definir el daño extra que podra causar el jefe
	private long bonusDamage;
	//phases: se va a usar para definir las fases que tendra el jefe
	private int phases;
	//Minions array
	private Array<Enemy> minions;
	//posicion
	private Vector2 initial;
	//Hilo del jefe
	private Thread t;

	/**
	 * @param spriteSrc
	 * @param name
	 * @param play
	 * @param pos
	 */
	public Boss(String spriteSrc, String name, Play play, Vector2 pos) {
		super(spriteSrc, name, play, pos);
		minions = new Array<Enemy>();
		initial = pos;
	}

	/**
	 * Ilumina graficos en pantalla
	 * @param sb
	 */
	@Override
	protected void render(SpriteBatch sb) {
		for (Enemy minion : minions) {
			minion.render(sb);
		}
		super.render(sb);
	}

	/**
	 * Este metodo funciona para revivir al jefe
	 **/
	public void revive(){
	}

	/**
	 * Este metodo funciona para crear enemigos de apoyo para el jefe y
	 *retorna una variable tipo Enemy
	 *@return Type Enemy
	 **/
	public void createEnemy(){
		Enemy ene = new Boss("player","Minion", getPlay(), initial/*.set(getPosition().x,getPosition().y+getPosition().y)*/);
		ene.setConfigurations("minion.lua",getIndicators().spawnHealth,0);
		minions.add(ene);
		new Thread(ene).start();
		Gdx.app.debug("Imp","Minion created "+ene.getPosition()+"=="+getPosition());
	}
}
