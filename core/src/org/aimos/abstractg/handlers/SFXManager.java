package org.aimos.abstractg.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Array;

import org.aimos.abstractg.core.Launcher;
import org.aimos.abstractg.handlers.factory.Factory;

/**
 * Created by Angyay0 on 09/10/2015.
 */

public class SFXManager implements Factory {

    private static float VOLUME = 0.4f;

    private Array<SoundID> queue;

    public SFXManager(){
        queue = new Array(10);
    }

    public void playInQueue(String key){
        queue.add( play(key) );
    }

    public void stopInQueue(String key){
        for(SoundID sid: queue.toArray() ){
            if( key.equals( sid.getKey() ) ){
                stop(sid);
                break;
            }
        }
    }

    public SoundID play(String key){
        return new SoundID(Launcher.res.getSound(key).play(),key);
    }

    public void stop(SoundID sound){
        Launcher.res.getSound(sound.getKey()).stop(sound.getID());
    }

    public class SoundID {
        private long id;
        private String key;

        public SoundID(long id){
            this.id = id;
        }

        public SoundID(long id,String key){
            this.id = id;
            this.key = key;
        }

        public long getID(){
            return id;
        }

        public String getKey(){ return key; }

    }

}
