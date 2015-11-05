package org.aimos.dim.android;

import android.app.Activity;

import org.aimos.abstractg.handlers.SocialMedia;

/**
 * Created by angyay0 on 05/11/2015.
 */

public class AndroidSocialMedia implements SocialMedia {

    private Activity activity;

    public AndroidSocialMedia(Activity activity){
        this.activity = activity;
    }

    @Override
    public void shareOnFacebook(String text, String score) {

    }

    @Override
    public void shareOnFacebook(String text, Object img) {

    }

    @Override
    public void shareOnFacebook(String text) {

    }

    @Override
    public void shareOnTwitter(String text, String score) {

    }

    @Override
    public void shareOnTwitter(String text, Object img) {

    }

    @Override
    public void shareOnTwitter(String text) {

    }
}
