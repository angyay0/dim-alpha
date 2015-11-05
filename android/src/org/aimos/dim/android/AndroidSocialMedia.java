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
