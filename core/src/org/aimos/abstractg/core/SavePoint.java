package org.aimos.abstractg.core;

import com.badlogic.gdx.utils.Array;
import org.aimos.abstractg.physics.Weapon;

/**

 * Created by DiegoArmando on 14/10/2015.
 */
public class SavePoint {

    private String chapter;
    private int world;
    private long coins;
    private long enemiesKilled;
    private Array<Weapon> weapons;

    public SavePoint(){}

    /*public SavePoint(String chapter,int world,Player player){
        this.chapter = chapter;
        this.world = world;
        this.coins = player.getMoney();
        this.weapons = player.getWeapons();
        this.enemiesKilled= player.getEnemiesKilled();

    }*/

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public void setWorld(int world) {
        this.world = world;
    }

    public void setCoins(long coins) {
        this.coins = coins;
    }

    public void setEnemiesKilled(long enemiesKilled) {
        this.enemiesKilled = enemiesKilled;
    }

    public void setWeapons(Array<Weapon> weapons) {
        this.weapons = weapons;
    }

    public String getChapter() {
        return chapter;
    }

    public int getWorld() {
        return world;
    }

    public long getCoins() {
        return coins;
    }

    public long getEnemiesKilled() {
        return enemiesKilled;
    }


    static class Profile{
        private long tCoins;
        private long tEnemiesK;
        private Array<Weapon> weapons;

        public Profile(){

        }

        public long gettCoins() {
            return tCoins;
        }

        public void settCoins(long tCoins) {
            this.tCoins = tCoins;
        }

        public long gettEnemiesK() {
            return tEnemiesK;
        }

        public void settEnemiesK(long tEnemiesK) {
            this.tEnemiesK = tEnemiesK;
        }

        public Array<Weapon> getWeapons() {
            return weapons;
        }

        public void setWeapons(Array<Weapon> weapons) {
            this.weapons = weapons;
        }
    }
}

