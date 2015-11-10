//Player
package org.aimos.abstractg.character;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import org.aimos.abstractg.core.JsonIO;
import org.aimos.abstractg.gamestate.Play;
import org.aimos.abstractg.handlers.Constants;
import org.aimos.abstractg.physics.Coin;
import org.aimos.abstractg.physics.DroppedWeapon;
import org.aimos.abstractg.physics.Weapon;

/**
 * Clase que define al jugador
 *
 * @author EinarGretch, Angyay0
 * @version 1.0.3
 * @date 07/09/2015
 * @updated 14/09/2015
 * @company AIMOS Studio
 **/

public class Player extends Character {
    //lista de armas
    private Array<Weapon> weapons;
    //enemigos eliminados
    private long enemiesKilled = 0;
    //velocidad de caida
    public boolean checkVelocityY = false;
    //score
    private int coinCount = 0;
    //hilo
    public volatile boolean running = true;
    //
    int count = 0;

    /**
     * Creates a new character
     *
     * @param spriteSrc
     * @param name
     * @param play
     * @param pos
     */
    public Player(String spriteSrc, String name, Play play, Vector2 pos) {
        super(spriteSrc, name, play, pos);
        weapons = new Array<Weapon>();
        setStats(400,10);
     //   chainShape = true;
    //    loadScript("ia_agents.lua");
       // loadScript();
    //    setSelfToScript();

      //  Thread t = new Thread(this);
    //    t.start();
    }

    /**
     * crea Cuerpo
     * @param pos
     */
    @Override
    protected final void createBodyExtra(Vector2 pos) {
        for (Fixture fixture : getBody().getFixtureList()) {
            Filter f = fixture.getFilterData();
            f.groupIndex = (short) (Constants.BIT.CHARACTER.BIT() | Constants.BIT.PLAYER.BIT());
        }
    }

    /**
     * establece animacion
     */
    @Override
    protected final void setExtraAnimations() {

    }

    /**
     * remueve cuerpo
     */
    @Override
    public void die() {
        getPlay().remove(this);
    }

    /**
     * Hilo
     */
    @Override
    public void run() {
        while(running){
            act();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(count < 100){
                count++;
            }else{
                running = false;
            }

        }
    }

    /**
     * agrega monedas
     * @param c
     */
    public void addMoney(Coin c) {
        addMoney(c.getValue());
        coinCount+=1;
    }

    /**
     * agrega armas
     * @param w
     */
    public void addWeapon(DroppedWeapon w) {
        weapons.add(w.getWeapon());
        setWeapon(w.getWeapon());
    }

    /**
     * checa si tiene el minimo de monedas para salir del mundo
     * @param min
     * @return boolean
     */
    public boolean hasMinimumCoins(int min){
        return (coinCount>=min);
    }

    /**
     * establece arma
     * @param i
     */
    public void setWeapon(int i){
        setWeapon(weapons.get(i));
    }

    /**
     * establece armas
     * @param w
     */
    public void setWeapons(Array<Weapon> w){
        weapons = w;
    }

    /**
     * agrega arma
     * @param w
     */
    public void addWeapon(Weapon w){
        weapons.add(w);
    }

    /**
     * retorna total de armas
     * @return weapon
     */
    public Array<Weapon> getWeapons(){
        return weapons;
    }

    /**
     * contabiliza las muerte
     * @return enemiesKilled
     */
    public long getEnemiesKilled() {
        return enemiesKilled;
    }

    /**
     * Visualiza pantalla ganador
     */
    public void setWinMapLevel(){ getPlay().setWin(true);   }

}
