package org.aimos.abstractg.handlers;

/**
 * Created by DiegoArmando on 05/10/2015.
 */
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;


import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializable;
import com.badlogic.gdx.utils.OrderedMap;




public class GameConfiguration {
    //Variable de almacenamiento en JSON
    private Preferences preferences;
    // Instancia de la clase
    public static GameConfiguration config;

    /**
     * metodo constructor de la clase e inicializaciones
     */
    public GameConfiguration(){
        super();
        preferences = Gdx.app.getPreferences("configurations");
        if(!preferences.contains("soundOn")) {
            setData();
        }
    }

    /**
     * Genera la instancia
     * @return retorna la instancia
     */
    public static GameConfiguration getInstance() {
        if(config == null){
            config = new GameConfiguration();
        }
        return config;
    }

    /**
     * Actualiza las configuraciones de inicio a true
     */
    private void setData() {
        preferences.putBoolean("soundOn", true);
        preferences.putBoolean("musicOn",true);
        preferences.putBoolean("fxOn",true);
        preferences.flush();
    }

    /**
     * busca el parametro music
     * @return retorna el valor de music
     */
    public boolean getMusic(){
        return preferences.getBoolean("musicOn");
    }

    /**
     * busca el parametro sound
     * @return retorna el valor de sound
     */
    public boolean getSound(){
        return preferences.getBoolean("soundOn");
    }

    /**
     * busca el parametro fx
     * @return retorna el valor de fx
     */
    public boolean getFx(){
        return preferences.getBoolean("fxOn");
    }

    /**
     * Actualiza el estado de music
     * @param music boolean
     */
    public void saveMusic(boolean music){
        preferences.putBoolean("musicOn",music);
        preferences.flush();
    }

    /**
     * Actualiza el estado de sound
     * @param sound boolean
     */
    public void saveSound(boolean sound){
        preferences.putBoolean("musicOn",sound);
        preferences.flush();
    }

    /**
     * Actualiza el estado de fx
     * @param fx boolean
     */
    public void saveFx(boolean fx){
        preferences.putBoolean("musicOn",fx);
        preferences.flush();
    }
}
