package org.aimos.dim.android;

import android.content.Context;

import com.facebook.FacebookSdk;

import org.aimos.abstractg.handlers.SocialMedia;

/**
 * Created by angyay0 on 05/11/2015.
 */

public class AndroidSocialMedia implements SocialMedia {

    private Context context;

    public AndroidSocialMedia(Context context){
        this.context = context;
        FacebookSdk.sdkInitialize(context);
    }

    /**
     * Metodo que funciona para compartir en facebook tu puntuacion y un pequeño texto
     * @param text
     * @param score
     */
    @Override
    public void shareOnFacebook(String text, String score) {

    }

    /**
     * Metodo que funciona para compartir en facebook una imagen con un texto
     * @param text
     * @param img
     */
    @Override
    public void shareOnFacebook(String text, Object img) {

    }

    /**
     * Metodo que funciona para compartir un texto en facebook,
     * @param text
     */
    @Override
    public void shareOnFacebook(String text) {

    }

    /**
     * Metodo para compartir en twitter puntuacion y un texto pequeño
     * @param text
     * @param score
     */
    @Override
    public void shareOnTwitter(String text, String score) {

    }

    /**
     * Metodo que funciona para compartir en twitter una imagen con un texto
     * @param text
     * @param img
     */
    @Override
    public void shareOnTwitter(String text, Object img) {

    }

    /**
     * Metodo que funciona compartir en la cuenta de twitter un texto
     * @param text
     */
    @Override
    public void shareOnTwitter(String text) {

    }
}
