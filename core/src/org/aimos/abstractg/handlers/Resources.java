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

    private HashMap<String, TextureAtlas> atlas;
    private HashMap<String, Music> music;
    private HashMap<String, Sound> sounds;
    private HashMap<String, Texture> textures;
    private HashMap<String, FileHandle> luas;

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

    public void loadTexture(String path) {
        loadTexture(path, nameFromPath(path));
    }
    public void loadTexture(String path, String key) {
        Texture tex = new Texture(Gdx.files.internal(path));
        tex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        textures.put(key, tex);
    }
    public Texture getTexture(String key) {
        return textures.get(key);
    }
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

    public void loadAtlas(String path) {
        loadAtlas(path, nameFromPath(path));
    }
    public void loadAtlas(String path, String key) {
        TextureAtlas atl = new TextureAtlas(Gdx.files.internal(path));
        atlas.put(key, atl);
    }
    public TextureAtlas getAtlas(String key) {
        return atlas.get(key);
    }
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

    public void loadMusic(String path) {
        loadMusic(path, nameFromPath(path));
    }
    public void loadMusic(String path, String key) {
        Music m = Gdx.audio.newMusic(Gdx.files.internal(path));
        music.put(key, m);
    }
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

    public void loadSound(String path) {
        loadSound(path, nameFromPath(path));
    }
    public void loadSound(String path, String key) {
        Sound sound = Gdx.audio.newSound(Gdx.files.internal(path));
        sounds.put(key, sound);
    }
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

    public HashMap getAllSounds(){
        return sounds;
    }

    //Load JSON

    /*********/
	/* other */
    /*********/

    //public void removeAll() {
		/*Iterator<Map.Entry<String, Texture>> iter1 = textures.entrySet().iterator();
		while(iter1.hasNext()) {
			Texture tex = iter1.next().getValue();
			tex.dispose();
			iter1.remove();
		}
		Iterator<Map.Entry<String, Music>> iter2 = music.entrySet().iterator();
		while(iter2.hasNext()) {
			Music music = iter2.next().getValue();
			music.dispose();
			iter2.remove();
		}
		Iterator<Map.Entry<String, Sound>> iter3 = sounds.entrySet().iterator();
		while(iter3.hasNext()) {
			Sound sound = iter3.next().getValue();
			sound.dispose();
			iter3.remove();
		}*/
    /*    for(Object o : textures.values()) {
            Texture tex = (Texture) o;
            tex.dispose();
        }
        textures.clear();
        for(Object o : music.values()) {
            Music music = (Music) o;
            music.dispose();
        }
        music.clear();
        for(Object o : sounds.values()) {
            Sound sound = (Sound) o;
            sound.dispose();
        }
        sounds.clear();
    }*/

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
