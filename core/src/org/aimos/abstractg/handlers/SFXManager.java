package org.aimos.abstractg.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Array;
import org.aimos.abstractg.core.Launcher;
/**
 * Created by Angyay0 on 09/10/2015.
 */


public class SFXManager{
    //variable para volumen del audio
    private static float VOLUME = 0.4f;
    //Almacena la cola de los sonidos
    private Array<SoundID> queue;

    /**
     * Metodo constructor e inicializacion de variable
     */
    public SFXManager(){
        queue = new Array(10);
    }

    /**
     *
     * @param key
     */
    public void playInQueue(String key){
        queue.add( play(key) );
    }

    /**
     *
     * @param key
     */
    public void stopInQueue(String key){
        for(SoundID sid: queue.toArray() ){
            if( key.equals( sid.getKey() ) ){
                stop(sid);
                break;
            }
        }
    }

    /**
     * busca el sonido
     * @param key clave del sonido
     * @return retorna sonido previamente buscado
     */
    public SoundID play(String key){
        return new SoundID(Launcher.res.getSound(key).play(),key);
    }

    /**
     * Detiene el sonido
     * @param sound
     */
    public void stop(SoundID sound){
        Launcher.res.getSound(sound.getKey()).stop(sound.getID());
    }

    /**
     * Identifica los sonidos
     */
    public class SoundID {
        //identificador del sonido
        private long id;
        //Clave del sonido
        private String key;

        /**
         * metodo constructor
         * @param id idenfiticador
         */
        public SoundID(long id){
            this.id = id;
        }

        /**
         * Metodo constructor
         * @param id identificador
         * @param key clave
         */
        public SoundID(long id,String key){
            this.id = id;
            this.key = key;
        }

        /**
         * Obtiene el identificador
         * @return id
         */
        public long getID(){
            return id;
        }

        /**
         * @return clave
         */
        public String getKey(){ return key; }
    }

}
