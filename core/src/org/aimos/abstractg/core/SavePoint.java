package org.aimos.abstractg.core;

import com.badlogic.gdx.utils.Array;
import org.aimos.abstractg.physics.Weapon;

/**

 * Created by DiegoArmando on 14/10/2015.
 */
public class SavePoint {
    //variable para el nombre del nivel
    private String chapter;
    //variable conocer el mundo
    private int world;
    //variable para total de monedas
    private long coins;
    //variable para enemigos eliminados
    private long enemiesKilled;
    //variable de armas en coleccion del jugador
    private Array<Weapon> weapons;

    /**
     *metodo constructor
     */
    public SavePoint(){}

    /**
     * establece el nombre del nivel
     * @param chapter
     */
    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    /**
     * establece el mundo
     * @param world
     */
    public void setWorld(int world) {
        this.world = world;
    }

    /**
     * establece la cantidad de monedas
     * @param coins
     */
    public void setCoins(long coins) {
        this.coins = coins;
    }

    /**
     * establece la cantidad de enemigos
     * @param enemiesKilled
     */
    public void setEnemiesKilled(long enemiesKilled) {
        this.enemiesKilled = enemiesKilled;
    }

    /**
     * establece las armas
     * @param weapons
     */
    public void setWeapons(Array<Weapon> weapons) {
        this.weapons = weapons;
    }

    /**
     * retorna el nivel
     * @return String
     */
    public String getChapter() {return chapter;}

    /**
     * @return mundo
     */
    public int getWorld() {
        return world;
    }

    /**
     * @return monedas
     */
    public long getCoins() {
        return coins;
    }

    /**
     * @return enemigos
     */
    public long getEnemiesKilled() {
        return enemiesKilled;
    }

    /**
     * clase anonima profile
     */
    static class Profile{
        //variable para las monedas
        private long tCoins;
        //variable para los enemigos
        private long tEnemiesK;
        //variable para armas
        private Array<Weapon> weapons;

        /**
         *metodo contructor
         */
        public Profile(){}

        /**
         * @return monedas obtenidas por el jugador en todo el juego
         */
        public long gettCoins() {
            return tCoins;
        }

        /**
         * establece las monedas
         * @param tCoins
         */
        public void settCoins(long tCoins) {
            this.tCoins = tCoins;
        }

        /**
         * @return long
         */
        public long gettEnemiesK() {
            return tEnemiesK;
        }

        /**
         * establece lo enemigos
         * @param tEnemiesK
         */
        public void settEnemiesK(long tEnemiesK) {
            this.tEnemiesK = tEnemiesK;
        }

        /**
         * @return array de weapons
         */
        public Array<Weapon> getWeapons() {
            return weapons;
        }

        /**
         * establece las armas
         * @param weapons
         */
        public void setWeapons(Array<Weapon> weapons) {
            this.weapons = weapons;
        }
    }
}

