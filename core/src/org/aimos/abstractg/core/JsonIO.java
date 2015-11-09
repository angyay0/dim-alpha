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
    //Lectura o escritura de algun JSON
    private static Json json = new Json();
    //Variable donde se almacena la cantidad "valor" monedas obtenidas
    public static long coins = 0;
    //Total de enemigos eliminados en partida
    public static long enemy = 0;
    //Opciones de armas del personaje
    public static Array<Weapon> weapons = null;
    //variable
    public static String chapter;
    //variable
    public static int world;
    //Almacena los nombres del niveles
    public static Array<String> levelName;
    //Almacena los icono de los niveles
    public static Array<String> iconName;
    //Almacena los mapas
    public static Array<String> tmxName;

    /**
     * Almacena en un JSON los datos "monedas,enemigos eliminados,armas,mundo" por nivel
     * @param player
     * @param chapter
     * @param world
     */
    public static void savePlay(Player player,String chapter, int world){
        SavePoint save = new SavePoint();
        save.setChapter(chapter);
        save.setCoins(player.getMoney());
        save.setEnemiesKilled(player.getEnemiesKilled());
        save.setWeapons(player.getWeapons());
        save.setWorld(world);
        FileHandle file = Gdx.files.local("dimensionsDir/"+chapter+"_"+world+".json");
        file.writeString(json.toJson(save), false);
    }

    /**
     * Lee los datos del nivel para hacer una actualizacion
     * @param chapter
     * @param world
     */
    public static void readPlay(String chapter,int world){
        FileHandle file = Gdx.files.local("dimensionsDir/"+chapter+"_"+world+".json");
        if(file.exists()) {
            SavePoint jsonR = json.fromJson(SavePoint.class, file.read());
        }
    }

    /**
     * Almacena el perfil del jugador, donde se guardan los valores de enemigos eliminados
     * total de monedas conseguidas y sus armas
     * @param weapons
     * @param coins
     * @param enemy
     */
    public static void saveProfile(Array<Weapon> weapons, long coins, long enemy){
        SavePoint.Profile save =new SavePoint.Profile();
        save.setWeapons(weapons);
        save.settCoins(coins);
        save.settEnemiesK(enemy);
        FileHandle file = Gdx.files.local("dimensionsDir/profile.json");
        file.writeString(json.toJson(save), false);

    }

    /**
     *
     * @return Total de monedas obtenidas en el juego
     */
    public static String readProfileTScore(){
        FileHandle file = Gdx.files.local("dimensionsDir/profile.json");
        if(file.exists()) {
            SavePoint.Profile profile = json.fromJson(SavePoint.Profile.class, file.read());
            return String.valueOf(profile.gettCoins());
        }else
            return "0";
    }

    /**
     * Lee el perfil completo del jugador
     * @return boolean
     */
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

    /**
     * Lee el JSON de los mundos y guarda los datos "nivel,icono,txm"
     * @param world
     */
    public static void ReadJSON(String world){
        JsonValue json = new JsonReader().parse(Gdx.files.internal("data/chapter.json"));
        levelName = new Array<String>();
        tmxName = new Array<String>();
        iconName= new Array<String>();
        JsonValue levelJson = json.get(world);
            for (JsonValue lvelJson : levelJson.iterator()) // iterator() returns a list of children
            {
                levelName.add(lvelJson.getString("name"));
                iconName.add(lvelJson.getString("icon"));
                tmxName.add(lvelJson.getString("tmx"));
            }
    }

    /**
     * @return un array con los nombre de los niveles
     */
    public static Array<String> setLevelName(){return levelName;}

    /**
     * @return Array de iconos
     */
    public static Array<String> setIconName(){
        return iconName;
    }

    /**
     * @return Array con nombre de mapas
     */
    public static Array<String> setTmxName(){
        return tmxName;
    }


}
