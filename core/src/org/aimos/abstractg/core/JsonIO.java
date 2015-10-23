package org.aimos.abstractg.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import org.aimos.abstractg.character.Player;
import org.aimos.abstractg.physics.Weapon;

/**
 * Created by DiegoArmando on 15/10/2015.
 */
public class JsonIO {
    private static Json json = new Json();

    //sirve para lectura del json interno de la partida
    public static long coins = 0;
    public static long enemy = 0;
    public static Array<Weapon> weapons = null;
    public static String chapter;
    public static int world;
    //Para leer el JSON de los niveles
    public static Array<String> levelName;
    public static Array<String> iconName;
    public static Array<String> tmxName;
    //


    public static void savePlay(Player player,String chapter, int world){
        SavePoint save = new SavePoint();
        save.setChapter(chapter);
        save.setCoins(player.getMoney());
        save.setEnemiesKilled(player.getEnemiesKilled());
        save.setWeapons(player.getWeapons());
        save.setWorld(world);
        FileHandle file = Gdx.files.local("dimensionsDir/"+chapter+"_"+world+".json");
        file.writeString(json.toJson(save), false);
        Gdx.app.debug("Eventoooooo!", json.prettyPrint(save));
    }

    public static void readPlay(String chapter,int world){
        FileHandle[] files = Gdx.files.local("dimensionsDir/").list();
        for(FileHandle file: files) {
            Gdx.app.debug("EVENTOOOO!!!", file.name());
        }
        FileHandle file = Gdx.files.local("dimensionsDir/"+chapter+"_"+world+".json");
        if(file.exists()) {
            SavePoint jsonR = json.fromJson(SavePoint.class, file.read());
        }
    }

    public static void saveProfile(Array<Weapon> weapons, long coins, long enemy){
        SavePoint.Profile save =new SavePoint.Profile();
        save.setWeapons(weapons);
        save.settCoins(coins);
        save.settEnemiesK(enemy);
        FileHandle file = Gdx.files.local("dimensionsDir/profile.json");
        file.writeString(json.toJson(save), false);

    }

    public static boolean readProfile(){
        FileHandle file = Gdx.files.local("dimensionsDir/profile.json");
        if(file.exists()) {
            SavePoint.Profile profile = json.fromJson(SavePoint.Profile.class, file.read());
            coins = profile.gettCoins();
            enemy = profile.gettEnemiesK();
            weapons=profile.getWeapons();
            Gdx.app.debug("Almacenado",json.prettyPrint(profile));
            return true;
        }
        return false;
    }

    public static void ReadJSON(String world){
        JsonValue json = new JsonReader().parse(Gdx.files.internal("data/chapter.json"));
        levelName = new Array<String>();
        JsonValue levelJson = json.get(world);
            for (JsonValue lvelJson : levelJson.iterator()) // iterator() returns a list of children
            {
                levelName.add(lvelJson.getString("name"));
                //iconName.add(lvelJson.getString("icon"));
                //tmxName.add(lvelJson.getString("tmx"));
            }
    }

    public static Array<String> setLevelName(){return levelName;}
    public static Array<String> setIconName(){
        return iconName;
    }
    public static Array<String> setTmxName(){
        return tmxName;
    }


}
