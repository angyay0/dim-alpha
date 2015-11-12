package org.aimos.abstractg.handlers;

import com.badlogic.gdx.audio.Music;

/**
 * Created by DiegoArmando on 02/10/2015.
 * @version 1.0
 */
public class AudioManager {
    //public static AudioManager getInstance() {return ourInstance;}

    //Variable para la instancia del audio
    private static AudioManager ourInstance;
    //Variable
    private Music audio;

    /**
     * Metodo constructor de la clase
     */
    private AudioManager() {
    }

    /**
     * Genera una instancia o retorna la creada
     * @return una instancia del la clase
     */
    public static AudioManager getInstance() {
        if (ourInstance == null) {
            ourInstance = new AudioManager();
        }
        return ourInstance;
    }

    /**
     * se establece el audio a reproducir
     * @param audio
     */
    public void initializeAudio(Music audio) {
        stopAudio();
        this.audio = audio;
    }

    /**
     * Se reproduce el audio con el volumen y un ciclo
     * @param vol
     * @param loop
     */
    public void play(float vol, boolean loop) {
        audio.setVolume(vol);
        audio.setLooping(loop);
        if (GameConfiguration.getInstance().getMusic()) {
            audio.play();
        }
    }

    /**
     * Detiene el audio
     */
    public void stopAudio() {
        if (audio != null) audio.stop();
    }

    /**
     * Verifica si algo en reproduccion
     * @return
     */
    public boolean isPlaying() {
        return audio.isPlaying();
    }

    /**
     * despausa el audio
     */
    public void play(){audio.play();}

    /**
     * metodo para continuar con el audio
     */
    public void continueAudio(Music audio){
        if(audio.equals("field")){
            if(GameConfiguration.getInstance().getMusic()){
                this.audio.play();
            }
        }else{
            this.audio = audio;
            if(GameConfiguration.getInstance().getMusic()){
                this.audio.play();
            }
        }
    }
}
