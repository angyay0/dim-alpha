package org.aimos.abstractg.character;

/**
 * Created by Angel on 30/10/2015.
 */
public class Indicators{
    public int min_health = 8;
    public int change_health = 80;
    public int spawnHealth = 7;
    public int final_change = 20;
    public int hp_per_tic = 4;
    public int behavior = 2;
    public int shieldHP = 0;
    public float spawnRate = 0.25f; //1/4 per sec
    public int enemyToSpawn = 1; //minions
    public int limitSpawn = 1;
    public int spawnTimes = 0;

    public boolean shielded = false;
    public boolean recover = true;
    public boolean spawner = true;

    public void addSpawn(){
        spawnTimes++;
    }
}