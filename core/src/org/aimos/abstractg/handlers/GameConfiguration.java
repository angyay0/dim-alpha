package org.aimos.abstractg.handlers;

/**
 * Created by DiegoArmando on 05/10/2015.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;


public class GameConfiguration {

    private Preferences preferences;
    private static GameConfiguration config;

    public GameConfiguration() {
        preferences = Gdx.app.getPreferences("configurations");
        if (!preferences.contains("soundOn")) {
            setData();
        }
    }

    public static GameConfiguration getInstance() {
        if (config == null) {
            config = new GameConfiguration();
        }
        return config;
    }

    private void setData() {
        preferences.putBoolean("soundOn", true);
        preferences.putBoolean("musicOn", true);
        preferences.putBoolean("fxOn", true);
        preferences.flush();
    }


    public boolean getMusic() {
        return preferences.getBoolean("musicOn");
    }

    public boolean getSound() {
        return preferences.getBoolean("soundOn");
    }


    public boolean getFx() {
        return preferences.getBoolean("fxOn");
    }

    public void saveMusic(boolean music) {
        preferences.putBoolean("musicOn", music);
        preferences.flush();
    }

    public void saveSound(boolean sound) {
        preferences.putBoolean("musicOn", sound);
        preferences.flush();
    }

    public void saveFx(boolean fx) {
        preferences.putBoolean("musicOn", fx);
        preferences.flush();
    }
}
