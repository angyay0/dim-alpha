//abstractg->character->Unique
package org.aimos.abstractg.character;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import org.aimos.abstractg.physics.Hazard.EFFECT_TYPE;

/**
 * Representa el personaje especial
 * que es como un enemigo pero con peculiaridades
 *
 * @version 1.0.3
 * @date 07/09/2015
 * @updated 14/09/2015
 * @author Angyay0,Gretch
 * @company AIMOS Studio
 *
 **/

 public class Unique extends Enemy {

	//Dano extra del personaje unique
	public long bonusDamage;

	//Titulo de personalidad para el personaje
	public String title;

	//Tipo de Efecto sobre el personaje si aplica
	public EFFECT_TYPE type;

	//Multiplicador de dano
	public float multiplier;

	/**
	 * Creates a new character
	 *
	 * @param spriteSrc
	 * @param name
	 * @param world
	 */
	protected Unique(String spriteSrc, String name, World world, Vector2 pos) {
		super(spriteSrc, name, world, pos);
	}
}
