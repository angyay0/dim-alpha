package org.aimos.abstractg.handlers;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

/**
 * Created by EinarGretch on 28/09/2015.
 */
public class Resources {
    //Almacena los atlas visualizados
    private HashMap<String, TextureAtlas> atlas;
    //Almacena la musica del juego
    private HashMap<String, Music> music;
    //Alamcena los sonidos del juego
    private HashMap<String, Sound> sounds;
    //Almacena las imagenes del juego
    private HashMap<String, Texture> textures;
    //Almacena la inteligencia
    private HashMap<String, FileHandle> luas;

    /**
     * Metodo constructor para inicializar variables
     */
    public Resources() {
        atlas = new HashMap<String, TextureAtlas>();
        textures = new HashMap<String, Texture>();
        music = new HashMap<String, Music>();
        sounds = new HashMap<String, Sound>();
        luas = new HashMap<String, FileHandle>();
    }


    /***********/
	/* Texture */
    /***********/

    /**
     * Carga las imagenes y asigna clave por default
     * @param path ruta de la imagen
     */
    public void loadTexture(String path) {
        loadTexture(path, nameFromPath(path));
    }

    /**
     * Carga las imagenes y asigna clave manual
     * @param path
     * @param key
     */
    public void loadTexture(String path, String key) {
        Texture tex = new Texture(Gdx.files.internal(path));
        tex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        textures.put(key, tex);
    }

    /**
     * busca la imagen especifica
     * @param key clave generada o asignada manual
     * @return
     */
    public Texture getTexture(String key) {
        return textures.get(key);
    }

    /**
     * Elimina las imagenes
     * @param key
     */
    public void removeTexture(String key) {
        Texture tex = textures.get(key);
        if(tex != null) {
            textures.remove(key);
            tex.dispose();
        }
    }

    /***********/
	/* Texture Atlas */
    /***********/
    /**
     * Carga los atlas, y  genera clave default
     * @param path ruta del atlas
     */
    public void loadAtlas(String path) {
        loadAtlas(path, nameFromPath(path));
    }

    /**
     * Carga los atlas y asigna clave
     * @param path ruta atlas
     * @param key clave manual
     */
    public void loadAtlas(String path, String key) {
        TextureAtlas atl = new TextureAtlas(Gdx.files.internal(path));
        atlas.put(key, atl);
    }

    /**
     * obtiene el atlas
     * @param key
     * @return retorna atlas especifico
     */
    public TextureAtlas getAtlas(String key) {
        return atlas.get(key);
    }

    /**
     * Elimina atlas
     * @param key
     */
    public void removeAtlas(String key) {
        TextureAtlas atl = atlas.get(key);
        if(atl != null) {
            atlas.remove(key);
            atl.dispose();
        }
    }

    /*********/
	/* Music */
    /*********/

    /**
     * Carga los audios del juego, y genera clave default
     * @param path
     */
    public void loadMusic(String path) {
        loadMusic(path, nameFromPath(path));
    }

    /**
     * Carga los audios del juego y asigna clave
     * @param path
     * @param key
     */
    public void loadMusic(String path, String key) {
        Music m = Gdx.audio.newMusic(Gdx.files.internal(path));
        music.put(key, m);
    }

    /**
     * Obtiene el audio
     * @param key
     * @return retorna el audio especifico
     */
    public Music getMusic(String key) {
        return music.get(key);
    }
    public void removeMusic(String key) {
        Music m = music.get(key);
        if(m != null) {
            music.remove(key);
            m.dispose();
        }
    }

    /*******/
	/* SFX */
    /*******/
    /**
     * Carga los sonidos y genera clave default
     * @param path
     */
    public void loadSound(String path) {
        loadSound(path, nameFromPath(path));
    }

    /**
     * Carga los sonidos y asigna clave
     * @param path
     * @param key
     */
    public void loadSound(String path, String key) {
        Sound sound = Gdx.audio.newSound(Gdx.files.internal(path));
        sounds.put(key, sound);
    }

    /**
     * Obtiene sonido
     * @param key
     * @return retorna sonido
     */
    public Sound getSound(String key) {
        return sounds.get(key);
    }
    public void removeSound(String key) {
        Sound sound = sounds.get(key);
        if(sound != null) {
            sounds.remove(key);
            sound.dispose();
        }
    }

    /**
     * Obtiene todos los sonidos
      * @return retorna los sonidos cargados
     */
    public HashMap getAllSounds(){
        return sounds;
    }

    /**
     * Genera la clave default a partir del nombre del archivo
     * @param path ruta
     * @return clave default
     */
    public static String nameFromPath(String path){
        int slashIndex = path.lastIndexOf('/');
        String key;
        if(slashIndex == -1) {
            key = path.substring(0, path.lastIndexOf('.'));
        }
        else {
            key = path.substring(slashIndex + 1, path.lastIndexOf('.'));
        }
        return key;
    }
}
