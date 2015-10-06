package org.aimos.abstractg.handlers;

import com.badlogic.gdx.audio.Music;


import org.aimos.abstractg.handlers.GameConfiguration;

/**
 * Created by DiegoArmando on 02/10/2015.
 * @version 1.0
 */
public class AudioManager {

   // private static AudioManager ourInstance = new AudioManager();
   //public static AudioManager getInstance() {return ourInstance;}

    private static AudioManager ourInstance;
    private Music audio;

    private AudioManager() {
        super();
    }

    public static AudioManager getInstance() {
        if (ourInstance == null) {
            ourInstance = new AudioManager();
        }
        return ourInstance;
    }

    public void initializeAudio(Music audio){
        stopAudio();
        this.audio = audio;
    }

    public void play(float vol,boolean loop){
        audio.setVolume(vol);
        audio.setLooping(loop);
        if(GameConfiguration.getInstance().getMusic()){
           audio.play();
        }
    }

    public void stopAudio(){
        if(audio != null) audio.stop();
    }

    public boolean isPlaying(){
        return audio.isPlaying();
    }

    public void play(){
        audio.play();
    }


    //metodo para leer los ajustes previos y checar
    public boolean setting(){
        return false;
    }




}
