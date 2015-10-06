package org.aimos.abstractg.handlers;

/**
 * Created by DiegoArmando on 05/10/2015.
 */
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.sun.org.apache.xml.internal.utils.Constants;


public class GameConfiguration {
    //JSON
    FileHandle file = Gdx.files.local("preferences.json");
    private Json jsonPref;
    ///

    private Preferences preferences;
    public static GameConfiguration confg ;

    public GameConfiguration(){
        super();
        preferences = Gdx.app.getPreferences("configurations");
        if(preferences == null){
            setData();
        }
    }

    public static GameConfiguration getInstance() {
        if(confg == null){
            confg = new GameConfiguration();
        }
        return confg;
    }

    private void setData() {
        preferences.putBoolean("soundOn", false);
        preferences.putBoolean("musicOn",false);
        preferences.putBoolean("fxOn",false);
        preferences.flush();
    }


    public boolean getMusic(){
        return preferences.getBoolean("musicOn");
    }

    public boolean getSound(){
        return preferences.getBoolean("soundOn");
    }


    public boolean getFx(){
        return preferences.getBoolean("fxOn");
    }

    public void saveMusic(boolean music){
        preferences.putBoolean("musicOn",music);
        preferences.flush();
    }

    public void saveSound(boolean sound){
        preferences.putBoolean("musicOn",sound);
        preferences.flush();
    }
    public void saveFx(boolean fx){
        preferences.putBoolean("musicOn",fx);
        preferences.flush();
    }

    ///Para probar con JSON
    public void loadSettings(){
        jsonPref = new Json();
        jsonPref.fromJson(GameConfiguration.class,file);
    }
}
